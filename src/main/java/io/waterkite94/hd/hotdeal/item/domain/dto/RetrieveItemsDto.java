package io.waterkite94.hd.hotdeal.item.domain.dto;

import java.time.LocalDateTime;

import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RetrieveItemsDto {

	private final Long itemId;
	private final String itemName;
	private final Cost cost;
	private final Boolean isPreOrderItem;
	private final LocalDateTime preOrderTime;
	private final String sellerId;
	private final String sellerName;

	@Builder
	public RetrieveItemsDto(Long itemId, String itemName, Cost cost, Boolean isPreOrderItem,
		LocalDateTime preOrderTime, String sellerId, String sellerName) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.cost = cost;
		this.isPreOrderItem = isPreOrderItem;
		this.preOrderTime = preOrderTime;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
	}
}
