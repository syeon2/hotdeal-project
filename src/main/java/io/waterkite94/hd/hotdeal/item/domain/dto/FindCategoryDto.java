package io.waterkite94.hd.hotdeal.item.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindCategoryDto {

	private Long id;

	private String name;

	@Builder
	private FindCategoryDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
