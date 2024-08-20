package io.waterkite94.hd.hotdeal.item.web.api.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.service.admin.CategoryService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddCategoryRequest;
import io.waterkite94.hd.hotdeal.item.web.api.response.AddCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryAdminController {

	private final CategoryService categoryService;

	@PostMapping
	public ApiResponse<AddCategoryResponse> addCategoryApi(@RequestBody @Valid AddCategoryRequest request) {
		Long savedCategoryId = categoryService.saveCategory(request.toServiceDto());

		return ApiResponse.ok(new AddCategoryResponse(savedCategoryId));
	}
}
