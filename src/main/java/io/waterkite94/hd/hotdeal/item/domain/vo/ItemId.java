package io.waterkite94.hd.hotdeal.item.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemId {

	private Long id;
	private String uuid;

	@Builder
	private ItemId(Long id, String uuid) {
		this.id = id;
		this.uuid = uuid;
	}

	public static ItemId of(Long id, String uuid) {
		return new ItemId(id, uuid);
	}
}
