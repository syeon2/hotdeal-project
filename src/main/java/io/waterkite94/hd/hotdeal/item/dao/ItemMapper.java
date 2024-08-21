package io.waterkite94.hd.hotdeal.item.dao;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.AddItemServiceDto;

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
			.status(addItemServiceDto.getStatus())
			.categoryId(addItemServiceDto.getCategoryId())
			.build();
	}
}
