package io.waterkite94.hd.hotdeal.item.domain.dto;

import java.time.LocalDateTime;

import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemDetailDto {

	private Long itemId;
	private String itemName;
	private Cost cost;
	private String introduction;
	private Boolean isPreOrderItem;
	private LocalDateTime preOrderTime;
	private LocalDateTime createdAt;
	private String sellerName;
	private String sellerId;

	@Builder
	public ItemDetailDto(Long itemId, String itemName, Cost cost, String introduction,
		Boolean isPreOrderItem, LocalDateTime preOrderTime, LocalDateTime createdAt, String sellerName,
		String sellerId) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.cost = cost;
		this.introduction = introduction;
		this.isPreOrderItem = isPreOrderItem;
		this.preOrderTime = preOrderTime;
		this.createdAt = createdAt;
		this.sellerName = sellerName;
		this.sellerId = sellerId;
	}
}
