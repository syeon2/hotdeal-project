package io.waterkite94.hd.hotdeal.item.api.presentation.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.item.api.application.admin.ItemAdminService;
import io.waterkite94.hd.hotdeal.item.api.presentation.request.AddItemRequest;
import io.waterkite94.hd.hotdeal.item.api.presentation.request.ChangeItemInfoRequest;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.AddItemResponse;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.ItemSuccessResponse;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.RetrieveRegisteredItemResponse;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.support.wrapper.CustomPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/items")
@RequiredArgsConstructor
public class ItemAdminController {

	private final ItemAdminService itemAdminService;

	@PostMapping
	public ApiResponse<AddItemResponse> registerItem(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@RequestBody @Valid AddItemRequest request
	) {
		Long savedItemId = itemAdminService.addItemWithMemberId(memberId, request.toServiceDto());

		return ApiResponse.ok(new AddItemResponse(savedItemId));
	}

	@PutMapping("/{itemId}/inactive")
	public ApiResponse<ItemSuccessResponse> deactivateItem(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@PathVariable Long itemId
	) {
		itemAdminService.changeItemStatusInactive(memberId, itemId);

		return ApiResponse.ok(new ItemSuccessResponse("상품의 상태를 비활성화로 변경하였습니다."));
	}

	@GetMapping
	public ApiResponse<CustomPage<RetrieveRegisteredItemResponse>> retrieveRegisteredItems(
		@RequestHeader("X-MEMBER-ID") String memberId,
		Pageable pageable
	) {
		CustomPage<RetrieveRegisteredItemResponse> convertResponseDto =
			CustomPage.convertDtoToResponse(
				itemAdminService.findAdminItems(memberId, pageable),
				RetrieveRegisteredItemResponse::of
			);

		return ApiResponse.ok(convertResponseDto);
	}

	@PutMapping("/{itemId}/info")
	public ApiResponse<ItemSuccessResponse> editItemInfo(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@PathVariable Long itemId,
		@RequestBody ChangeItemInfoRequest request
	) {
		itemAdminService.changeItemInfo(memberId, itemId, request.toServiceDto());

		return ApiResponse.ok(new ItemSuccessResponse("update item successfully"));
	}
}
