package io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.AddCategoryDto;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.FindCategoryDto;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.CategoryEntity;

@Component
public class CategoryMapper {

	public CategoryEntity toEntity(AddCategoryDto addCategoryDto) {
		return CategoryEntity.builder()
			.name(addCategoryDto.getName())
			.build();
	}

	public FindCategoryDto toServiceDto(CategoryEntity category) {
		return FindCategoryDto.builder()
			.id(category.getId())
			.name(category.getName())
			.build();
	}
}
