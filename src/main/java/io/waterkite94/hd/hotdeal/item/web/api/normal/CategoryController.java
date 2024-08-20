package io.waterkite94.hd.hotdeal.item.web.api.normal;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.service.admin.CategoryService;
import io.waterkite94.hd.hotdeal.item.web.api.response.FindCategoriesResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ApiResponse<List<FindCategoriesResponse>> findAllCategoriesApi() {
		List<FindCategoriesResponse> findCategories = categoryService.findAllCategories().stream()
			.map(FindCategoriesResponse::of)
			.toList();

		return ApiResponse.ok(findCategories);
	}
}
