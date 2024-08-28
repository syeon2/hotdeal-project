package io.waterkite94.hd.hotdeal.order.domain.dto;

import io.waterkite94.hd.hotdeal.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddOrderDto {

	private String uuid;
	private OrderStatus status;
	private String address;
	private String memberId;

	@Builder
	private AddOrderDto(String uuid, OrderStatus status, String address, String memberId) {
		this.uuid = uuid;
		this.status = status;
		this.address = address;
		this.memberId = memberId;
	}

}
