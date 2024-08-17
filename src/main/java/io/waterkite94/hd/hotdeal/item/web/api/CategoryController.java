package io.waterkite94.hd.hotdeal.item.web.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.domain.Category;
import io.waterkite94.hd.hotdeal.item.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ApiResponse<List<Category>> findAllCategories() {
		return ApiResponse.ok(categoryService.findAllCategories());
	}
}
