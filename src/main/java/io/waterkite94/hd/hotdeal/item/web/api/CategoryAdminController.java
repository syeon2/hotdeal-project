package io.waterkite94.hd.hotdeal.item.web.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.service.CategoryService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryAdminController {

	private final CategoryService categoryService;

	@PostMapping
	public ApiResponse<Long> addCategory(@RequestBody @Valid AddCategoryRequest request) {
		Long savedCategoryId = categoryService.saveCategory(request.toCategoryDomain());

		return ApiResponse.ok(savedCategoryId);
	}
}
