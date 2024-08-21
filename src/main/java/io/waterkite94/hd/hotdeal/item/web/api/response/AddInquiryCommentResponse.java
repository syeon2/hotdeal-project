package io.waterkite94.hd.hotdeal.item.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class AddInquiryCommentResponse {

	@JsonProperty("inquiry_comment_id")
	private Long inquiryCommentId;

	public AddInquiryCommentResponse(Long inquiryCommentId) {
		this.inquiryCommentId = inquiryCommentId;
	}
}
