package io.waterkite94.hd.hotdeal.item.domain.dto;

import java.time.LocalDateTime;

import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemId;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RetrieveRegisteredItemDto {

	private final ItemId itemId;
	private final String itemName;
	private final Cost cost;
	private final ItemType itemType;
	private final LocalDateTime preOrderSchedule;
	private final Integer quantity;
	private final LocalDateTime createdAt;
	private final Long categoryId;
	private final String categoryName;

	@Builder
	public RetrieveRegisteredItemDto(ItemId itemId, String itemName, Cost cost, ItemType itemType,
		LocalDateTime preOrderSchedule, Integer quantity, LocalDateTime createdAt, Long categoryId,
		String categoryName) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.cost = cost;
		this.itemType = itemType;
		this.preOrderSchedule = preOrderSchedule;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
}
