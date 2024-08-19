package io.waterkite94.hd.hotdeal.item.web.api.request;

import io.waterkite94.hd.hotdeal.item.domain.InquiryComment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddInquiryCommentRequest {

	private String comment;

	private Long itemInquiryId;

	private String memberId;

	@Builder
	private AddInquiryCommentRequest(String comment, Long itemInquiryId, String memberId) {
		this.comment = comment;
		this.itemInquiryId = itemInquiryId;
		this.memberId = memberId;
	}

	public InquiryComment toDomain() {
		return InquiryComment.builder()
			.comment(comment)
			.itemInquiryId(itemInquiryId)
			.memberId(memberId)
			.build();
	}
}
