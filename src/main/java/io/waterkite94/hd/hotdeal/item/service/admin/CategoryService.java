package io.waterkite94.hd.hotdeal.item.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.DuplicatedCategoryNameException;
import io.waterkite94.hd.hotdeal.item.dao.CategoryMapper;
import io.waterkite94.hd.hotdeal.item.dao.CategoryRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.CategoryEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.AddCategoryDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.FindCategoryDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Transactional
	public Long saveCategory(AddCategoryDto addCategoryDto) {
		validateCategoryName(addCategoryDto);

		CategoryEntity savedCategory = categoryRepository.save(
			categoryMapper.toEntity(addCategoryDto)
		);

		return savedCategory.getId();
	}

	/**
	 * 이후 category list를 수정하지 않기 때문에 불변 List로 반환
	 * Java 16에 추가된 toList()를 사용합니다. (unmodifiableList)
	 */
	@Transactional(readOnly = true)
	public List<FindCategoryDto> findAllCategories() {
		return categoryRepository.findAll().stream()
			.map(categoryMapper::toServiceDto)
			.toList();
	}

	private void validateCategoryName(AddCategoryDto addCategoryDto) {
		categoryRepository.findByName(addCategoryDto.getName()).ifPresent(category -> {
			throw new DuplicatedCategoryNameException("중복된 카테고리 이름입니다. : " + category.getName());
		});
	}
}
