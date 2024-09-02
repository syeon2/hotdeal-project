package io.waterkite94.hd.hotdeal.item.web.api.user;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.common.wrapper.CustomPage;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.service.user.ItemService;
import io.waterkite94.hd.hotdeal.item.web.api.request.ItemTypeRequest;
import io.waterkite94.hd.hotdeal.item.web.api.response.ItemDetailResponse;
import io.waterkite94.hd.hotdeal.item.web.api.response.RetrieveItemsResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping
	public ApiResponse<CustomPage<RetrieveItemsResponse>> retrieveItems(
		@RequestParam(value = "category-id") Long categoryId,
		@RequestParam(value = "type") ItemTypeRequest itemType,
		@RequestParam(required = false) String search,
		Pageable pageable
	) {
		CustomPage<RetrieveItemsResponse> convertedItems = CustomPage.convertDtoToResponse(
			itemService.findItems(categoryId, itemType.toVo(), search, pageable),
			RetrieveItemsResponse::of
		);

		return ApiResponse.ok(convertedItems);
	}

	@GetMapping("/{itemId}")
	public ApiResponse<ItemDetailResponse> retrieveItemDetail(@PathVariable Long itemId) {
		ItemDetailDto findItemDetail = itemService.findItemDetail(itemId);

		return ApiResponse.ok(ItemDetailResponse.of(findItemDetail));
	}
}
