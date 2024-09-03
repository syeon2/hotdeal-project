package io.waterkite94.hd.hotdeal.order.service.user;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.OutOfStockException;
import io.waterkite94.hd.hotdeal.common.util.UuidUtil;
import io.waterkite94.hd.hotdeal.item.dao.persistence.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.persistence.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.order.dao.persistence.OrderDetailMapper;
import io.waterkite94.hd.hotdeal.order.dao.persistence.OrderMapper;
import io.waterkite94.hd.hotdeal.order.dao.persistence.OrderRepository;
import io.waterkite94.hd.hotdeal.order.dao.persistence.entity.OrderEntity;
import io.waterkite94.hd.hotdeal.order.domain.OrderStatus;
import io.waterkite94.hd.hotdeal.order.domain.dto.AddAddressDto;
import io.waterkite94.hd.hotdeal.order.domain.dto.AddOrderDto;
import io.waterkite94.hd.hotdeal.order.domain.dto.AddOrderItemDto;
import io.waterkite94.hd.hotdeal.order.domain.dto.OrderDetailDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;
	private final OrderMapper orderMapper;
	private final OrderDetailMapper orderDetailMapper;

	@Transactional
	public String addNormalOrderWithOrderDetail(
		String memberId,
		AddAddressDto addAddressDto,
		List<AddOrderItemDto> orderItems
	) {
		List<OrderDetailDto> orderDetails = createOrderDetails(orderItems);

		OrderEntity savedOrderEntity = orderRepository.save(
			orderMapper.toEntity(initializeOrderDto(memberId, addAddressDto))
		);

		orderDetails.forEach(orderDetail -> {
			savedOrderEntity.addOrderDetail(orderDetailMapper.toEntity(orderDetail));
		});

		return savedOrderEntity.getUuid();
	}

	@Transactional
	public String addPreOrderItemWithOrderDetail(String memberId, AddAddressDto addAddressDto,
		AddOrderItemDto orderItem) {
		ItemEntity itemEntity = validateItemForPreOrder(orderItem);

		itemEntity.deductQuantity(orderItem.getQuantity());

		OrderEntity savedOrderEntity = orderRepository.save(
			orderMapper.toEntity(initializeOrderDto(memberId, addAddressDto))
		);

		savedOrderEntity.addOrderDetail(
			orderDetailMapper.toEntity(
				OrderDetailDto.from(orderItem, itemEntity.getPrice(), itemEntity.getDiscount())
			)
		);

		return savedOrderEntity.getUuid();
	}

	@Transactional
	public void changeOrderStatusToCancel(String orderUuid) {
		OrderEntity orderEntity = getOrderEntity(orderUuid);

		validateOrderStatus(orderEntity, OrderStatus.PAYMENT_COMPLETED, "주문 취소할 수 있는 단계가 아닙니다.");

		orderEntity.changeStatus(OrderStatus.ORDER_CANCELLED);
	}

	@Transactional
	public void changeOrderStatusToInDelivery(String orderUuid) {
		OrderEntity orderEntity = getOrderEntity(orderUuid);

		validateOrderStatus(orderEntity, OrderStatus.PAYMENT_COMPLETED, "배송을 진행할 수 있는 단계가 아닙니다.");

		orderEntity.changeStatus(OrderStatus.IN_DELIVERY);
	}

	@Transactional
	public void changeOrderStatusToCompleteDelivery(String orderUuid) {
		OrderEntity orderEntity = getOrderEntity(orderUuid);

		validateOrderStatus(orderEntity, OrderStatus.IN_DELIVERY, "배송을 완료할 수 있는 단계가 아닙니다.");

		orderEntity.changeStatus(OrderStatus.DELIVERY_COMPLETED);
	}

	@Transactional
	public void changeOrderStatusToInReturn(String orderUuid) {
		OrderEntity orderEntity = getOrderEntity(orderUuid);

		validateOrderStatus(orderEntity, OrderStatus.DELIVERY_COMPLETED, "반품할 수 있는 단계가 아닙니다.");
		validateReturnPeriod(orderEntity);

		orderEntity.changeStatus(OrderStatus.RETURN_IN_PROGRESS);
	}

	@Transactional
	public void changeOrderStatusToCompleteReturn(String orderUuid) {
		OrderEntity orderEntity = getOrderEntity(orderUuid);

		validateOrderStatus(orderEntity, OrderStatus.RETURN_IN_PROGRESS, "반품할 수 있는 단계가 아닙니다.");

		orderEntity.changeStatus(OrderStatus.RETURN_COMPLETED);
	}

	private List<OrderDetailDto> createOrderDetails(List<AddOrderItemDto> orderItems) {
		Map<Long, OrderDetailDto> orderDetails = new HashMap<>();

		for (AddOrderItemDto orderItem : orderItems) {
			ItemEntity itemEntity = itemRepository.findById(orderItem.getItemId())
				.orElseThrow(() -> new IllegalArgumentException("잘못된 상품 아이디를 압력하였습니다."));

			if (itemEntity.getType().equals(ItemType.PRE_ORDER)) {
				throw new IllegalArgumentException("예약 구매 상품은 일반 구매 상품과 함께 결제할 수 없습니다.");
			}

			OrderDetailDto orderDetail = OrderDetailDto.from(
				orderItem,
				itemEntity.getPrice(),
				itemEntity.getQuantity()
			);

			orderDetails.putIfAbsent(itemEntity.getId(), orderDetail);
		}

		return orderDetails.values().stream()
			.toList();
	}

	private AddOrderDto initializeOrderDto(String memberId, AddAddressDto addAddressDto) {
		return AddOrderDto.of(
			UuidUtil.createUuid(),
			OrderStatus.PAYMENT_COMPLETED,
			addAddressDto.convertAddress(),
			memberId
		);
	}

	private ItemEntity validateItemForPreOrder(AddOrderItemDto orderItem) {
		ItemEntity itemEntity = itemRepository.findItemEntityForQuantityUpdate(orderItem.getItemId())
			.orElseThrow(() -> new IllegalArgumentException("잘못된 상품 아이디입니다."));

		if (itemEntity.getType().equals(ItemType.NORMAL_ORDER)) {
			throw new IllegalArgumentException("일반 구매 상품은 예약 구매로 구매할 수 없습니다.");
		}

		if (itemEntity.getQuantity() - orderItem.getQuantity() < 0) {
			throw new OutOfStockException("재고가 부족합니다.");
		}

		return itemEntity;
	}

	private OrderEntity getOrderEntity(String orderUuid) {
		return orderRepository.findByUuid(orderUuid)
			.orElseThrow(() -> new IllegalArgumentException("잘못된 주문 아이디입니다."));
	}

	private void validateOrderStatus(OrderEntity orderEntity, OrderStatus orderStatus, String message) {
		if (!orderEntity.getStatus().equals(orderStatus)) {
			throw new IllegalArgumentException(message);
		}
	}

	private void validateReturnPeriod(OrderEntity orderEntity) {
		LocalDate createdAtDate = orderEntity.getCreatedAt().toLocalDate();
		LocalDate cutoffDate = LocalDate.now().minusDays(6);

		if (cutoffDate.isAfter(createdAtDate)) {
			throw new IllegalArgumentException("반품할 수 있는 날짜가 지났습니다.");
		}
	}
}
