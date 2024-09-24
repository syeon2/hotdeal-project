package io.waterkite94.hd.hotdeal.order.api.infrastructure.persistence;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.order.api.infrastructure.persistence.entity.OrderEntity;
import io.waterkite94.hd.hotdeal.order.api.domain.dto.AddOrderDto;

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
