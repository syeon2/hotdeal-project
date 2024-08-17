package io.waterkite94.hd.hotdeal.item.web.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.service.ItemAdminService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class ItemAdminController {

	private final ItemAdminService itemAdminService;

	@PostMapping
	public ApiResponse<String> addItemApi(@RequestBody @Valid AddItemRequest request) {
		String savedItemId = itemAdminService.addItem(request.toDomain());

		return ApiResponse.ok(savedItemId);
	}

	@DeleteMapping("/{itemId}")
	public ApiResponse<String> deleteItemApi(@PathVariable String itemId) {
		itemAdminService.deleteItem(itemId);

		return ApiResponse.ok("success");
	}
}
