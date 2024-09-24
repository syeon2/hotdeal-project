package io.waterkite94.hd.hotdeal.order.api.domain.dto;

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

	public static OrderDetailDto from(AddOrderItemDto addOrderItemDto, Integer price, Integer discount) {
		Integer totalPrice = price * addOrderItemDto.getQuantity();
		Integer totalDiscount = discount * addOrderItemDto.getQuantity();

		return OrderDetailDto.builder()
			.itemId(addOrderItemDto.getItemId())
			.quantity(addOrderItemDto.getQuantity())
			.totalPrice(totalPrice)
			.totalDiscount(totalDiscount)
			.build();
	}
}
