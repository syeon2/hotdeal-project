package io.waterkite94.hd.hotdeal.item.api.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.waterkite94.hd.hotdeal.item.api.domain.vo.ItemType;

public enum ItemTypeRequest {
	NONE,
	PRE_ORDER,
	NORMAL_ORDER;

	@JsonCreator
	public static ItemTypeRequest from(String value) {
		return ItemTypeRequest.valueOf(value.toUpperCase());
	}

	public ItemType toVo() {
		return switch (this) {
			case PRE_ORDER -> ItemType.PRE_ORDER;
			case NORMAL_ORDER -> ItemType.NORMAL_ORDER;
			default -> ItemType.NONE;
		};
	}
}
