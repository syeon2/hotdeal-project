package io.waterkite94.hd.hotdeal.item.api.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.item.api.application.admin.CategoryService;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.AddCategoryDto;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.FindCategoryDto;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.CategoryRepository;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.CategoryEntity;

class CategoryServiceTest extends IntegrationTestSupport {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryRepository categoryRepository;

	@BeforeEach
	void before() {
		categoryRepository.deleteAllInBatch();
	}

	@Test
	@Transactional
	@DisplayName(value = "카테고리를 저장합니다.")
	void saveCategory() {
		// given
		String name = "name";
		AddCategoryDto addCategoryDto = createAddCategoryDto(name);

		// when
		Long savedCategoryId = categoryService.saveCategory(addCategoryDto);

		// then
		Optional<CategoryEntity> findCategoryOptional = categoryRepository.findById(savedCategoryId);
		assertThat(findCategoryOptional).isPresent();
		assertThat(findCategoryOptional.get().getName()).isEqualTo(name);
	}

	@Test
	@Transactional
	@DisplayName(value = "카테고리들을 조회합니다.")
	void findAllCategory() {
		// given
		AddCategoryDto findCategoryDto = createAddCategoryDto("name");
		categoryService.saveCategory(findCategoryDto);

		// when
		List<FindCategoryDto> findAllCategories = categoryService.findAllCategories();

		// then
		assertThat(findAllCategories).hasSize(1);
	}

	private FindCategoryDto createCategoryDomain(String name) {
		return FindCategoryDto.builder()
			.name(name)
			.build();
	}

	private AddCategoryDto createAddCategoryDto(String name) {
		return new AddCategoryDto(name);
	}
}
