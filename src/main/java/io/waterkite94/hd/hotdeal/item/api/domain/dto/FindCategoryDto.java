package io.waterkite94.hd.hotdeal.item.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindCategoryDto {

	private final Long id;
	private final String name;

	@Builder
	private FindCategoryDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
