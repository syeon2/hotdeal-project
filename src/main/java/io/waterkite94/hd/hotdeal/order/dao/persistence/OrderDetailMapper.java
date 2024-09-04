package io.waterkite94.hd.hotdeal.order.dao.persistence;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.order.dao.persistence.entity.OrderDetailEntity;
import io.waterkite94.hd.hotdeal.order.domain.dto.OrderDetailDto;

@Component
public class OrderDetailMapper {

	public OrderDetailEntity toEntity(OrderDetailDto orderDetailDto) {
		return OrderDetailEntity.builder()
			.quantity(orderDetailDto.getQuantity())
			.totalDiscount(orderDetailDto.getTotalDiscount())
			.totalPrice(orderDetailDto.getTotalPrice())
			.itemId(orderDetailDto.getItemId())
			.build();
	}
}
