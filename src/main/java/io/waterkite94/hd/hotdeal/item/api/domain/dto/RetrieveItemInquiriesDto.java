package io.waterkite94.hd.hotdeal.item.api.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RetrieveItemInquiriesDto {

	private Long itemInquiryId;
	private String comment;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String memberName;

	@Builder
	public RetrieveItemInquiriesDto(Long itemInquiryId, String comment, LocalDateTime createdAt,
		LocalDateTime updatedAt,
		String memberName) {
		this.itemInquiryId = itemInquiryId;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.memberName = memberName;
	}
}
