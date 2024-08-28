package io.waterkite94.hd.hotdeal.order.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDetailDto {

	private final Integer quantity;
	private final Integer totalPrice;
	private final Integer totalDiscount;
	private final Long itemId;

	@Builder
	private OrderDetailDto(Integer quantity, Integer totalPrice, Integer totalDiscount, Long itemId) {
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.totalDiscount = totalDiscount;
		this.itemId = itemId;
	}
}
