package io.waterkite94.hd.hotdeal.item.web.api.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemInquiriesDto;
import lombok.Builder;

public class RetrieveItemInquiriesResponse {

	@JsonProperty("item_inquiry_id")
	private final Long itemInquiryId;

	@JsonProperty("comment")
	private final String comment;

	@JsonProperty("created_at")
	private final LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private final LocalDateTime updatedAt;

	@JsonProperty("member_name")
	private final String memberName;

	@Builder
	private RetrieveItemInquiriesResponse(Long itemInquiryId, String comment, LocalDateTime createdAt,
		LocalDateTime updatedAt, String memberName) {
		this.itemInquiryId = itemInquiryId;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.memberName = memberName;
	}

	public static RetrieveItemInquiriesResponse of(RetrieveItemInquiriesDto retrieveItemInquiriesDto) {
		return RetrieveItemInquiriesResponse.builder()
			.itemInquiryId(retrieveItemInquiriesDto.getItemInquiryId())
			.comment(retrieveItemInquiriesDto.getComment())
			.createdAt(retrieveItemInquiriesDto.getCreatedAt())
			.updatedAt(retrieveItemInquiriesDto.getUpdatedAt())
			.memberName(retrieveItemInquiriesDto.getMemberName())
			.build();
	}
}
