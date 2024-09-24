package io.waterkite94.hd.hotdeal.item.api.presentation.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.item.api.application.admin.CategoryService;
import io.waterkite94.hd.hotdeal.item.api.presentation.request.AddCategoryRequest;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.AddCategoryResponse;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryAdminController {

	private final CategoryService categoryService;

	@PostMapping
	public ApiResponse<AddCategoryResponse> addCategory(@RequestBody @Valid AddCategoryRequest request) {
		Long savedCategoryId = categoryService.saveCategory(request.toServiceDto());

		return ApiResponse.ok(new AddCategoryResponse(savedCategoryId));
	}
}
