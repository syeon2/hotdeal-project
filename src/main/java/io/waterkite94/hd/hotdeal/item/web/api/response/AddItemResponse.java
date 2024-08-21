package io.waterkite94.hd.hotdeal.item.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class AddItemResponse {

	@JsonProperty("item_id")
	private Long itemId;

	public AddItemResponse(Long itemId) {
		this.itemId = itemId;
	}
}
