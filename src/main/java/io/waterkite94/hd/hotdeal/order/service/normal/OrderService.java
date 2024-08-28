package io.waterkite94.hd.hotdeal.order.service.normal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.util.UuidUtil;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.order.dao.OrderDetailMapper;
import io.waterkite94.hd.hotdeal.order.dao.OrderMapper;
import io.waterkite94.hd.hotdeal.order.dao.OrderRepository;
import io.waterkite94.hd.hotdeal.order.dao.entity.OrderEntity;
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
	public String addOrderWithOrderDetail(String memberId, AddAddressDto addAddressDto,
		List<AddOrderItemDto> orderItems) {
		// TODO:: Item quantity와 price, discount를 계산
		List<OrderDetailDto> orderDetails = new ArrayList<>();

		for (AddOrderItemDto orderItem : orderItems) {
			ItemEntity itemEntity = itemRepository.findById(orderItem.getItemId())
				.orElseThrow(() -> new IllegalArgumentException("잘못된 상품 아이디를 압력하였습니다."));

			OrderDetailDto orderDetail = OrderDetailDto.builder()
				.itemId(orderItem.getItemId())
				.quantity(orderItem.getQuantity())
				.totalPrice(orderItem.getQuantity() * itemEntity.getPrice())
				.totalDiscount(orderItem.getQuantity() * itemEntity.getDiscount())
				.build();

			orderDetails.add(orderDetail);
		}

		// TODO:: Address 변환
		String convertedAddress = addAddressDto.getCity()
			.concat(addAddressDto.getState())
			.concat(addAddressDto.getAddress())
			.concat(addAddressDto.getZipcode());

		// TODO:: Order Entity 생성
		String createdOrderUuid = UuidUtil.createUuid();
		OrderEntity orderEntity = orderMapper.toEntity(AddOrderDto.builder()
			.uuid(createdOrderUuid)
			.status(OrderStatus.PAYMENT_COMPLETED)
			.address(convertedAddress)
			.memberId(memberId)
			.build()
		);
		OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

		// TODO:: order Detail Entity 생성
		for (OrderDetailDto orderDetail : orderDetails) {
			savedOrderEntity.addOrderDetail(
				orderDetailMapper.toEntity(orderDetail)
			);
		}

		return createdOrderUuid;
	}

	@Transactional
	public void changeOrderStatusToCancel(String orderUuid) {
		OrderEntity orderEntity = orderRepository.findByUuid(orderUuid)
			.orElseThrow(() -> new IllegalArgumentException("잘못된 주문 아이디입니다."));

		if (!orderEntity.getStatus().equals(OrderStatus.PAYMENT_COMPLETED)) {
			throw new IllegalArgumentException("주문 취소할 수 있는 단계가 아닙니다.");
		}

		orderEntity.changeStatus(OrderStatus.ORDER_CANCELLED);
	}

}
