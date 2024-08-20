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
import io.waterkite94.hd.hotdeal.item.domain.dto.FindAdminItemDto;
import io.waterkite94.hd.hotdeal.item.service.admin.ItemAdminService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemRequest;
import io.waterkite94.hd.hotdeal.item.web.api.response.AddItemResponse;
import io.waterkite94.hd.hotdeal.item.web.api.response.FindAdminItemResponse;
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

	@GetMapping
	public ApiResponse<CustomPage<FindAdminItemResponse>> findAdminItems(
		@RequestHeader("X-MEMBER-ID") String memberId,
		Pageable pageable
	) {
		Page<FindAdminItemDto> findItemsWithPage = itemAdminService.findAdminItems(memberId, pageable);
		CustomPage<FindAdminItemResponse> convertResponseDto = convertDtoToResponse(findItemsWithPage);

		return ApiResponse.ok(convertResponseDto);
	}

	private CustomPage<FindAdminItemResponse> convertDtoToResponse(Page<FindAdminItemDto> findItemsWithPage) {
		return CustomPage.of(
			findItemsWithPage.stream().map(FindAdminItemResponse::of).toList(),
			findItemsWithPage.getTotalElements());
	}
}
