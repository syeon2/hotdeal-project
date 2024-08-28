package io.waterkite94.hd.hotdeal.order.dao;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.order.dao.entity.OrderEntity;
import io.waterkite94.hd.hotdeal.order.domain.dto.AddOrderDto;

@Component
public class OrderMapper {

	public OrderEntity toEntity(AddOrderDto addOrderDto) {
		return OrderEntity.builder()
			.uuid(addOrderDto.getUuid())
			.status(addOrderDto.getStatus())
			.address(addOrderDto.getAddress())
			.memberId(addOrderDto.getMemberId())
			.build();
	}
}
