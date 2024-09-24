package io.waterkite94.hd.hotdeal.item.api.domain.vo;

public enum ItemStatus {
	ACTIVE("계정 활성화"),
	INACTIVE("계정 비활성화");

	private final String description;

	ItemStatus(String description) {
		this.description = description;
	}
}
