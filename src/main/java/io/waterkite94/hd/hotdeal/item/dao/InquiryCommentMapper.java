package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.dao.entity.InquiryCommentEntity;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemInquiryEntity;
import io.waterkite94.hd.hotdeal.item.domain.InquiryComment;

@Component
public class InquiryCommentMapper {

	public InquiryCommentEntity toEntity(InquiryComment inquiryComment, ItemInquiryEntity itemInquiryEntity) {
		return InquiryCommentEntity.builder()
			.comment(inquiryComment.getComment())
			.memberId(inquiryComment.getMemberId())
			.itemInquiry(itemInquiryEntity)
			.build();
	}

	public InquiryComment toDomain(InquiryCommentEntity inquiryComment) {
		return InquiryComment.builder()
			.comment(inquiryComment.getComment())
			.createdAt(inquiryComment.getCreatedAt())
			.updatedAt(inquiryComment.getUpdatedAt())
			.memberId(inquiryComment.getMemberId())
			.itemInquiryId(inquiryComment.getItemInquiry().getId())
			.build();
	}
}
