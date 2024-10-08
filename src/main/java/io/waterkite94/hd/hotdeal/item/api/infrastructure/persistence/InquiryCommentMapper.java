package io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.InquiryCommentDto;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.InquiryCommentEntity;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.ItemInquiryEntity;

@Component
public class InquiryCommentMapper {

	public InquiryCommentEntity toEntity(InquiryCommentDto inquiryComment, ItemInquiryEntity itemInquiryEntity) {
		return InquiryCommentEntity.builder()
			.comment(inquiryComment.getComment())
			.itemInquiry(itemInquiryEntity)
			.build();
	}

	public InquiryCommentDto toDomain(InquiryCommentEntity inquiryComment) {
		return InquiryCommentDto.builder()
			.comment(inquiryComment.getComment())
			.createdAt(inquiryComment.getCreatedAt())
			.updatedAt(inquiryComment.getUpdatedAt())
			.itemInquiryId(inquiryComment.getItemInquiry().getId())
			.build();
	}
}
