package io.waterkite94.hd.hotdeal.item.web.api.normal;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemBoardDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.item.service.normal.ItemService;
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

	@GetMapping("/search")
	public ApiResponse<List<ItemBoardDto>> searchItems(@RequestParam String word, @RequestParam Long offset) {
		List<ItemBoardDto> findItems = itemService.searchItemsToWord(word, offset);

		return ApiResponse.ok(findItems);
	}

	@GetMapping("/item/{itemId}")
	public ApiResponse<ItemBoardDto> searchItems(@PathVariable Long itemId) {
		ItemBoardDto itemDetail = itemService.findItemDetail(itemId);

		return ApiResponse.ok(itemDetail);
	}
}
