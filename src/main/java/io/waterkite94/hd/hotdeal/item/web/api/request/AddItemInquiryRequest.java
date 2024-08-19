package io.waterkite94.hd.hotdeal.item.web.api.request;

import io.waterkite94.hd.hotdeal.item.domain.ItemInquiry;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemInquiryRequest {

	private String comment;

	private String memberId;

	private Long itemId;

	@Builder
	private AddItemInquiryRequest(String comment, String memberId, Long itemId) {
		this.comment = comment;
		this.memberId = memberId;
		this.itemId = itemId;
	}

	public ItemInquiry toDomain() {
		return ItemInquiry.builder()
			.comment(comment)
			.memberId(memberId)
			.itemId(itemId)
			.build();
	}
}
