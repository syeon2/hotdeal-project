package io.waterkite94.hd.hotdeal.item.web.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemTypeRequest {
	NONE("none"),
	PRE_ORDER("preOrder"),
	NORMAL_ORDER("normalOrder");

	private final String value;

	ItemTypeRequest(String value) {
		this.value = value;
	}

	@JsonCreator
	public static ItemTypeRequest fromString(ItemTypeRequest string) {
		for (ItemTypeRequest itemTypeRequest : ItemTypeRequest.values()) {
			if (itemTypeRequest.getValue().equals(string)) {
				return itemTypeRequest;
			}
		}
		return null;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
