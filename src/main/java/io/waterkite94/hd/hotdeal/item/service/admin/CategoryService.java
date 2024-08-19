package io.waterkite94.hd.hotdeal.item.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.waterkite94.hd.hotdeal.item.dao.CategoryMapper;
import io.waterkite94.hd.hotdeal.item.dao.CategoryRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.CategoryEntity;
import io.waterkite94.hd.hotdeal.item.domain.Category;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	public Long saveCategory(Category category) {
		CategoryEntity savedCategory = categoryRepository.save(categoryMapper.toEntity(category));

		return savedCategory.getId();
	}

	public List<Category> findAllCategories() {
		return categoryRepository.findAll().stream()
			.map(categoryMapper::toDomain)
			.collect(Collectors.toList());
	}
}
