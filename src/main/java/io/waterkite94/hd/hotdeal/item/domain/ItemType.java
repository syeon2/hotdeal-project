package io.waterkite94.hd.hotdeal.item.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemType {
	NONE("none"),
	PRE_ORDER("preOrder"),
	NORMAL_ORDER("normalOrder");

	private final String value;

	ItemType(String value) {
		this.value = value;
	}

	@JsonCreator
	public static ItemType fromString(String string) {
		for (ItemType itemType : ItemType.values()) {
			if (itemType.getValue().equals(string)) {
				return itemType;
			}
		}
		return null;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
