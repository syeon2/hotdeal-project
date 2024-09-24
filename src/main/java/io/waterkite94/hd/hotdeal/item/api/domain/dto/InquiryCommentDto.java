package io.waterkite94.hd.hotdeal.item.api.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InquiryCommentDto {

	private final Long inquiryCommentId;
	private final String comment;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private final Long itemInquiryId;

	@Builder
	public InquiryCommentDto(Long inquiryCommentId, String comment, LocalDateTime createdAt, LocalDateTime updatedAt,
		Long itemInquiryId) {
		this.inquiryCommentId = inquiryCommentId;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.itemInquiryId = itemInquiryId;
	}
}
