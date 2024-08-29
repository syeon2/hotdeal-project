package io.waterkite94.hd.hotdeal.item.domain.dto;

import lombok.Getter;

@Getter
public class AddCategoryDto {

	private final String name;

	private AddCategoryDto(String name) {
		this.name = name;
	}

	public static AddCategoryDto of(String name) {
		return new AddCategoryDto(name);
	}
}
