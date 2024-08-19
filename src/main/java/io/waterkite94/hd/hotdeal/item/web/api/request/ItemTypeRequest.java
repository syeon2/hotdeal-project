package io.waterkite94.hd.hotdeal.item.web.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ItemTypeRequest {
	NONE,
	PRE_ORDER,
	NORMAL_ORDER;

	@JsonCreator
	public static ItemTypeRequest from(String value) {
		return ItemTypeRequest.valueOf(value.toUpperCase());
	}
}
