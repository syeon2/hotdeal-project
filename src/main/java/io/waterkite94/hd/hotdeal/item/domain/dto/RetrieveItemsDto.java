package io.waterkite94.hd.hotdeal.item.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RetrieveItemsDto {

	private Long itemId;
	private String itemName;
	private Integer price;
	private Integer discount;
	private Boolean isPreOrderItem;
	private LocalDateTime preOrderTime;
	private String sellerId;
	private String sellerName;

	@Builder
	public RetrieveItemsDto(Long itemId, String itemName, Integer price, Integer discount, Boolean isPreOrderItem,
		LocalDateTime preOrderTime, String sellerId, String sellerName) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
		this.discount = discount;
		this.isPreOrderItem = isPreOrderItem;
		this.preOrderTime = preOrderTime;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
	}
}
