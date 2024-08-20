package io.waterkite94.hd.hotdeal.item.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindItemDto {

	private Long id;

	private String name;

	private Integer price;

	private Integer discount;

	private Boolean isPreOrderItem;

	private LocalDateTime preOrderTime;

	private String sellerName;

	private String sellerId;

	@Builder
	public FindItemDto(Long id, String name, Integer price, Integer discount,
		boolean isPreOrderItem, LocalDateTime preOrderTime, String sellerName, String sellerId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.isPreOrderItem = isPreOrderItem;
		this.preOrderTime = preOrderTime;
		this.sellerName = sellerName;
		this.sellerId = sellerId;
	}
}
