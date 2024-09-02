package io.waterkite94.hd.hotdeal.order.domain.dto;

import io.waterkite94.hd.hotdeal.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddOrderDto {

	private final String uuid;
	private final OrderStatus status;
	private final String address;
	private final String memberId;

	@Builder
	private AddOrderDto(String uuid, OrderStatus status, String address, String memberId) {
		this.uuid = uuid;
		this.status = status;
		this.address = address;
		this.memberId = memberId;
	}

	public static AddOrderDto of(String uuid, OrderStatus status, String address, String memberId) {
		return new AddOrderDto(uuid, status, address, memberId);
	}

}
