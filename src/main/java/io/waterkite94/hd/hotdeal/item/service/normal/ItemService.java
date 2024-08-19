package io.waterkite94.hd.hotdeal.item.service.normal;

import java.util.List;

import org.springframework.stereotype.Service;

import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.domain.ItemType;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemBoardDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	public List<ItemBoardDto> searchItemsMain(Long categoryId, ItemType itemType, Long itemOffset) {
		return itemRepository.searchItemsByCategoryId(categoryId, itemType, itemOffset);
	}

	public List<ItemBoardDto> searchItemsToWord(String word, Long itemOffset) {
		return itemRepository.searchItemsContainsWord(word, itemOffset);
	}

	public ItemBoardDto findItemDetail(Long itemId) {
		return itemRepository.findItemDetail(itemId);
	}
}
