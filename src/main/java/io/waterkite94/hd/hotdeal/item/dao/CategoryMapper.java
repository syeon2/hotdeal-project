package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.dao.entity.CategoryEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.FindCategoryDto;

@Component
public class CategoryMapper {

	public CategoryEntity toEntity(FindCategoryDto findCategoryDto) {
		return CategoryEntity.builder()
			.name(findCategoryDto.getName())
			.build();
	}

	public FindCategoryDto toDomain(CategoryEntity category) {
		return FindCategoryDto.builder()
			.id(category.getId())
			.name(category.getName())
			.build();
	}
}
