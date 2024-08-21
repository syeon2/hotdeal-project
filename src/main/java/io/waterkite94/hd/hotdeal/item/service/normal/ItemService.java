package io.waterkite94.hd.hotdeal.item.service.normal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	public Page<RetrieveItemsDto> findItems(Long categoryId, ItemType type, String search,
		Pageable pageable) {
		return itemRepository.searchItemsByCategoryId(categoryId, type, search, pageable);
	}

	public ItemDetailDto findItemDetail(Long itemId) {
		return itemRepository.findItemDetail(itemId);
	}
}
