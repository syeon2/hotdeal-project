package io.waterkite94.hd.hotdeal.item.api.domain.vo;

import lombok.Getter;

@Getter
public enum ItemType {
	NONE("일반 판매 상품"),
	PRE_ORDER("예약 판매 상품"),
	NORMAL_ORDER("일반 판매 상품");

	private final String description;

	ItemType(String description) {
		this.description = description;
	}
}
