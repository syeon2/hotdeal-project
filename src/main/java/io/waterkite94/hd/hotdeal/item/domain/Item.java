package io.waterkite94.hd.hotdeal.item.domain;

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

	private String memberId;

	private Long categoryId;

	@Builder
	public Item(Long id, String itemId, String name, Integer price, Integer discount,
		String introduction,
		ItemType type, String memberId, Long categoryId) {
		this.id = id;
		this.itemId = itemId;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.introduction = introduction;
		this.type = type;
		this.memberId = memberId;
		this.categoryId = categoryId;
	}

	public Item initialize(String itemId) {
		return this
			.withItemId(itemId);
	}

	private Item withItemId(String itemId) {
		return new Item(this.id, itemId, this.name, this.price, this.discount, this.introduction, this.type,
			this.memberId, this.categoryId);
	}
}
