package io.waterkite94.hd.hotdeal.item.web.api.normal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.common.wrapper.CustomPage;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.service.normal.ItemService;
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
		@RequestParam Long categoryId,
		@RequestParam(value = "type") ItemTypeRequest itemType,
		@RequestParam(required = false) String search,
		Pageable pageable
	) {
		Page<RetrieveItemsDto> findItems =
			itemService.findItems(categoryId, itemType.toVo(), search, pageable);
		List<RetrieveItemsResponse> convertedItems = findItems.getContent().stream()
			.map(RetrieveItemsResponse::of)
			.toList();

		return ApiResponse.ok(CustomPage.of(convertedItems, findItems.getTotalElements()));
	}

	@GetMapping("/item/{itemId}")
	public ApiResponse<ItemDetailResponse> retrieveItemDetail(@PathVariable Long itemId) {
		ItemDetailDto findItemDetail = itemService.findItemDetail(itemId);

		return ApiResponse.ok(ItemDetailResponse.of(findItemDetail));
	}
}
