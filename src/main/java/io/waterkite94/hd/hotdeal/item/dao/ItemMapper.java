package io.waterkite94.hd.hotdeal.item.dao;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.AddItemServiceDto;
import io.waterkite94.hd.hotdeal.item.domain.Item;

@Component
public class ItemMapper {

	public ItemEntity toEntity(AddItemServiceDto addItemServiceDto, LocalDateTime preOrderSchedule) {
		return ItemEntity.builder()
			.uuid(addItemServiceDto.getItemId().getUuid())
			.name(addItemServiceDto.getName())
			.price(addItemServiceDto.getCost().getPrice())
			.discount(addItemServiceDto.getCost().getDiscount())
			.introduction(addItemServiceDto.getIntroduction())
			.type(addItemServiceDto.getType())
			.preOrderTime(preOrderSchedule)
			.memberId(addItemServiceDto.getMemberId())
			.categoryId(addItemServiceDto.getCategoryId())
			.build();
	}

	public Item toDomain(ItemEntity item) {
		return Item.builder()
			.itemId(item.getUuid())
			.name(item.getName())
			.price(item.getPrice())
			.discount(item.getDiscount())
			.introduction(item.getIntroduction())
			.type(item.getType())
			.preOrderTime(item.getPreOrderTime())
			.memberId(item.getMemberId())
			.categoryId(item.getCategoryId())
			.build();
	}
}
