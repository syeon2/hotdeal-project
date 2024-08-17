package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.Item;

@Component
public class ItemMapper {

	public ItemEntity toEntity(Item item) {
		return ItemEntity.builder()
			.itemId(item.getItemId())
			.name(item.getName())
			.price(item.getPrice())
			.discount(item.getDiscount())
			.introduction(item.getIntroduction())
			.type(item.getType())
			.memberId(item.getMemberId())
			.categoryId(item.getCategoryId())
			.build();
	}

	public Item toDomain(ItemEntity item) {
		return Item.builder()
			.itemId(item.getItemId())
			.name(item.getName())
			.price(item.getPrice())
			.discount(item.getDiscount())
			.introduction(item.getIntroduction())
			.type(item.getType())
			.memberId(item.getMemberId())
			.categoryId(item.getCategoryId())
			.build();
	}
}
