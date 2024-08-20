package io.waterkite94.hd.hotdeal.item.service.normal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.domain.dto.FindItemDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.SearchItemListDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	public Page<SearchItemListDto> searchPreOrderItems(Long categoryId, ItemType type, Pageable pageable) {
		return itemRepository.searchItemsByCategoryId(categoryId, type, pageable);
	}

	public List<FindItemDto> searchItemsToWord(String word, Long itemOffset) {
		return itemRepository.searchItemsContainsWord(word, itemOffset);
	}

	public FindItemDto findItemDetail(Long itemId) {
		return itemRepository.findItemDetail(itemId);
	}
}
