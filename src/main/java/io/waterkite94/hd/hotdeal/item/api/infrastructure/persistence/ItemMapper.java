package io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.AddItemServiceDto;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.ItemEntity;

@Component
public class ItemMapper {

	public ItemEntity toEntity(AddItemServiceDto addItemServiceDto, LocalDateTime preOrderSchedule) {
		return ItemEntity.builder()
			.uuid(addItemServiceDto.getItemId().getUuid())
			.name(addItemServiceDto.getName())
			.price(addItemServiceDto.getCost().getPrice())
			.discount(addItemServiceDto.getCost().getDiscount())
			.quantity(addItemServiceDto.getQuantity())
			.introduction(addItemServiceDto.getIntroduction())
			.type(addItemServiceDto.getType())
			.preOrderTime(preOrderSchedule)
			.memberId(addItemServiceDto.getMemberId())
			.status(addItemServiceDto.getStatus())
			.categoryId(addItemServiceDto.getCategoryId())
			.build();
	}
}
