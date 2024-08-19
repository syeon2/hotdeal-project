package io.waterkite94.hd.hotdeal.item.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InquiryComment {

	private String comment;

	private String memberId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Long itemInquiryId;

	@Builder
	private InquiryComment(String comment, String memberId, LocalDateTime createdAt, LocalDateTime updatedAt,
		Long itemInquiryId) {
		this.comment = comment;
		this.memberId = memberId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.itemInquiryId = itemInquiryId;
	}
}
