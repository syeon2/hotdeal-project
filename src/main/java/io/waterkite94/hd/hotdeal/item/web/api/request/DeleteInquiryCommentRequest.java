package io.waterkite94.hd.hotdeal.item.web.api.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteInquiryCommentRequest {

	private Long inquiryCommentId;

	private String memberId;

	@Builder
	private DeleteInquiryCommentRequest(Long inquiryCommentId, String memberId) {
		this.inquiryCommentId = inquiryCommentId;
		this.memberId = memberId;
	}
}
