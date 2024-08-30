package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.dao.entity.CategoryEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.AddCategoryDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.FindCategoryDto;

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
