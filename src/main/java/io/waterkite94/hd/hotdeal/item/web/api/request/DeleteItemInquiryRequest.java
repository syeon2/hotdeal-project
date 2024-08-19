package io.waterkite94.hd.hotdeal.item.web.api.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteItemInquiryRequest {

	private Long itemInquiryId;

	private String memberId;

	@Builder
	private DeleteItemInquiryRequest(Long itemInquiryId, String memberId) {
		this.itemInquiryId = itemInquiryId;
		this.memberId = memberId;
	}
}
