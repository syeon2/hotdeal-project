package io.waterkite94.hd.hotdeal.item.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Item {

	private Long id;

	private String itemId;

	private String name;

	private Integer price;

	private Integer discount;

	private String introduction;

	private ItemType type;

	private LocalDateTime preOrderTime;

	private String memberId;

	private Long categoryId;

	@Builder
	public Item(Long id, String itemId, String name, Integer price, Integer discount,
		String introduction,
		ItemType type, LocalDateTime preOrderTime, String memberId, Long categoryId) {
		this.id = id;
		this.itemId = itemId;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.introduction = introduction;
		this.type = type;
		this.preOrderTime = preOrderTime;
		this.memberId = memberId;
		this.categoryId = categoryId;
	}

	public Item initialize(String memberId, String itemId) {
		if (preOrderTime == null) {
			preOrderTime = LocalDateTime.now();
		}

		return this
			.withItemId(itemId)
			.withMemberId(memberId);
	}

	private Item withItemId(String itemId) {
		return new Item(this.id, itemId, this.name, this.price, this.discount, this.introduction, this.type,
			this.preOrderTime, this.memberId, this.categoryId);
	}

	private Item withMemberId(String memberId) {
		return new Item(this.id, this.itemId, this.name, this.price, this.discount, this.introduction, this.type,
			this.preOrderTime, memberId, this.categoryId);
	}
}
