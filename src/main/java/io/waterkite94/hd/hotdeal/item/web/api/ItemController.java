package io.waterkite94.hd.hotdeal.item.web.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.domain.ItemType;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemBoardDto;
import io.waterkite94.hd.hotdeal.item.service.ItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping
	public ApiResponse<List<ItemBoardDto>> searchItems(
		@RequestParam Long categoryId,
		@RequestParam String itemType,
		@RequestParam Long offset
	) {
		ItemType type = null;

		if (itemType.equals("pre-order")) {
			type = ItemType.PRE_ORDER;
		} else if (itemType.equals("normal-order")) {
			type = ItemType.NORMAL_ORDER;
		} else {
			type = ItemType.NONE;
		}

		List<ItemBoardDto> findItems = itemService.searchItemsMain(categoryId, type, offset);

		return ApiResponse.ok(findItems);
	}
}
