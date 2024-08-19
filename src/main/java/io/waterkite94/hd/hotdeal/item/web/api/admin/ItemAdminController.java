package io.waterkite94.hd.hotdeal.item.web.api.admin;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.service.admin.ItemAdminService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemRequest;
import io.waterkite94.hd.hotdeal.item.web.api.response.AddItemResponse;
import io.waterkite94.hd.hotdeal.item.web.api.response.ItemSuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/items")
@RequiredArgsConstructor
public class ItemAdminController {

	private final ItemAdminService itemAdminService;

	@PostMapping
	public ApiResponse<AddItemResponse> addItemApi(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@RequestBody @Valid AddItemRequest request
	) {
		Long savedItemId = itemAdminService.addItemWithMemberId(memberId, request.toServiceDto());

		return ApiResponse.ok(new AddItemResponse(savedItemId));
	}

	@PutMapping("/{itemId}")
	public ApiResponse<ItemSuccessResponse> changeItemStatusInactiveApi(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@PathVariable Long itemId
	) {
		itemAdminService.changeItemStatusInactive(memberId, itemId);

		return ApiResponse.ok(new ItemSuccessResponse("delete item successfully"));
	}
}
