package io.waterkite94.hd.hotdeal.item.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Category {

	private Long id;

	private String name;

	@Builder
	private Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
