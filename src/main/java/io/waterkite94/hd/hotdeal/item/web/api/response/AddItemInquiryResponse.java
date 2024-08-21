package io.waterkite94.hd.hotdeal.item.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class AddItemInquiryResponse {

	@JsonProperty("item_inquiry_id")
	private Long itemInquiryId;

	public AddItemInquiryResponse(Long itemInquiryId) {
		this.itemInquiryId = itemInquiryId;
	}
}
