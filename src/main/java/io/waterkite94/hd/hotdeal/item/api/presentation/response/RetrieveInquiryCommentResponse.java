package io.waterkite94.hd.hotdeal.item.api.presentation.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.InquiryCommentDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RetrieveInquiryCommentResponse {

	@JsonProperty("comment")
	private final String comment;

	@JsonProperty("created_at")
	private final LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private final LocalDateTime updatedAt;

	@Builder
	private RetrieveInquiryCommentResponse(String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static RetrieveInquiryCommentResponse of(InquiryCommentDto inquiryCommentDto) {
		return RetrieveInquiryCommentResponse.builder()
			.comment(inquiryCommentDto.getComment())
			.createdAt(inquiryCommentDto.getCreatedAt())
			.updatedAt(inquiryCommentDto.getUpdatedAt())
			.build();
	}
}
