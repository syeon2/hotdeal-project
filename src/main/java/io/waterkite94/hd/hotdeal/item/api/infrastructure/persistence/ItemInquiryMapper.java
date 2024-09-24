package io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.api.domain.ItemInquiry;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.ItemInquiryEntity;

@Component
public class ItemInquiryMapper {

	public ItemInquiryEntity toEntity(ItemInquiry itemInquiry, ItemEntity item) {
		return ItemInquiryEntity.builder()
			.comment(itemInquiry.getComment())
			.memberId(itemInquiry.getMemberId())
			.item(item)
			.build();
	}

	public ItemInquiry toDomain(ItemInquiryEntity itemInquiry) {
		return ItemInquiry.builder()
			.id(itemInquiry.getId())
			.comment(itemInquiry.getComment())
			.createdAt(itemInquiry.getCreatedAt())
			.updatedAt(itemInquiry.getUpdatedAt())
			.memberId(itemInquiry.getMemberId())
			.itemId(itemInquiry.getItem().getId())
			.build();
	}
}
