package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.item.dao.entity.CategoryEntity;
import io.waterkite94.hd.hotdeal.item.domain.Category;

@Component
public class CategoryMapper {

	public CategoryEntity toEntity(Category category) {
		return CategoryEntity.builder()
			.name(category.getName())
			.build();
	}

	public Category toDomain(CategoryEntity category) {
		return Category.builder()
			.id(category.getId())
			.name(category.getName())
			.build();
	}
}
