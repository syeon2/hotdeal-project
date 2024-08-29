package io.waterkite94.hd.hotdeal.item.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.item.dao.CategoryMapper;
import io.waterkite94.hd.hotdeal.item.dao.CategoryRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.CategoryEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.FindCategoryDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Transactional
	public Long saveCategory(FindCategoryDto findCategoryDto) {
		CategoryEntity savedCategory = categoryRepository.save(categoryMapper.toEntity(findCategoryDto));

		return savedCategory.getId();
	}

	@Transactional
	public List<FindCategoryDto> findAllCategories() {
		return categoryRepository.findAll().stream()
			.map(categoryMapper::toDomain)
			.toList();
	}
}
