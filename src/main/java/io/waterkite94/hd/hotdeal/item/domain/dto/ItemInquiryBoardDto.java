package io.waterkite94.hd.hotdeal.item.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemInquiryBoardDto {

	private Long itemInquiryId;

	private String comment;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String memberId;

	private String memberName;

	@Builder
	public ItemInquiryBoardDto(Long itemInquiryId, String comment, LocalDateTime createdAt, LocalDateTime updatedAt,
		String memberId, String memberName) {
		this.itemInquiryId = itemInquiryId;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.memberId = memberId;
		this.memberName = memberName;
	}
}
