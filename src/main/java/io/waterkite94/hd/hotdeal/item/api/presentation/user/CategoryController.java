package io.waterkite94.hd.hotdeal.item.api.presentation.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.item.api.application.admin.CategoryService;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.FindCategoriesResponse;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ApiResponse<List<FindCategoriesResponse>> retrieveAllCategories() {
		List<FindCategoriesResponse> findCategories =
			FindCategoriesResponse.listOf(categoryService.findAllCategories());

		return ApiResponse.ok(findCategories);
	}
}
