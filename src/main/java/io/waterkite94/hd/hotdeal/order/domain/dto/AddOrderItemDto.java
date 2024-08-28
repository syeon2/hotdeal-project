package io.waterkite94.hd.hotdeal.order.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddOrderItemDto {

	private final Long itemId;
	private final Integer quantity;

	@Builder
	private AddOrderItemDto(Long itemId, Integer quantity) {
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public static AddOrderItemDto of(Long itemId, Integer quantity) {
		return new AddOrderItemDto(itemId, quantity);
	}
}
