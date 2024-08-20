package io.waterkite94.hd.hotdeal.item.domain.dto;

import java.time.LocalDateTime;

import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindAdminItemDto {

	private Long itemId;
	private String itemUuid;
	private String itemName;
	private Integer price;
	private Integer discount;
	private ItemType itemType;
	private LocalDateTime preOrderSchedule;
	private LocalDateTime createdAt;
	private Long categoryId;
	private String categoryName;

	@Builder
	public FindAdminItemDto(Long itemId, String itemUuid, String itemName, Integer price, Integer discount,
		ItemType itemType, LocalDateTime preOrderSchedule, LocalDateTime createdAt, Long categoryId,
		String categoryName) {
		this.itemId = itemId;
		this.itemUuid = itemUuid;
		this.itemName = itemName;
		this.price = price;
		this.discount = discount;
		this.itemType = itemType;
		this.preOrderSchedule = preOrderSchedule;
		this.createdAt = createdAt;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
}
