package io.waterkite94.hd.hotdeal.item.web.api.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.common.wrapper.CustomPage;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveRegisteredItemDto;
import io.waterkite94.hd.hotdeal.item.service.admin.ItemAdminService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.ChangeItemInfoRequest;
import io.waterkite94.hd.hotdeal.item.web.api.response.AddItemResponse;
import io.waterkite94.hd.hotdeal.item.web.api.response.ItemSuccessResponse;
import io.waterkite94.hd.hotdeal.item.web.api.response.RetrieveRegisteredItemResponse;
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
		Page<RetrieveRegisteredItemDto> findItemsWithPage = itemAdminService.findAdminItems(memberId, pageable);
		CustomPage<RetrieveRegisteredItemResponse> convertResponseDto = convertDtoToResponse(findItemsWithPage);

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

	private CustomPage<RetrieveRegisteredItemResponse> convertDtoToResponse(
		Page<RetrieveRegisteredItemDto> findItemsWithPage) {
		return CustomPage.of(
			findItemsWithPage.stream().map(RetrieveRegisteredItemResponse::of).toList(),
			findItemsWithPage.getTotalElements());
	}
}
