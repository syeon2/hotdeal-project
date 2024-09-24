package io.waterkite94.hd.hotdeal.item.api.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteInquiryCommentRequest {

	@JsonProperty("inquiry_comment_id")
	@NotNull(message = "문의 답글 아이디는 필수 값입니다.")
	private Long inquiryCommentId;

	public DeleteInquiryCommentRequest(Long inquiryCommentId) {
		this.inquiryCommentId = inquiryCommentId;
	}
}
