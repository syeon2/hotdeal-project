package io.waterkite94.hd.hotdeal.item.api.presentation.response;

import lombok.Getter;

@Getter
public class ItemSuccessResponse {

	private String message;

	public ItemSuccessResponse(String message) {
		this.message = message;
	}
}
