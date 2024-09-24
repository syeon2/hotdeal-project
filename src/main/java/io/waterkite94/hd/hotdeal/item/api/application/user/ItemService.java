package io.waterkite94.hd.hotdeal.item.api.application.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.api.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.ItemRepository;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.redis.ItemQuantityRedisAdapter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;
	private final ItemQuantityRedisAdapter itemQuantityRedisAdapter;

	@Transactional(readOnly = true)
	public Page<RetrieveItemsDto> findItems(Long categoryId, ItemType type, String search,
		Pageable pageable) {
		return itemRepository.searchItemsByCategoryId(categoryId, type, search, pageable);
	}

	@Transactional(readOnly = true)
	public ItemDetailDto findItemDetail(Long itemId) {
		return itemRepository.findItemDetail(itemId);
	}

	public void deductQuantity(Long itemId, Integer quantity) {
		itemQuantityRedisAdapter.deductItemQuantity(itemId, quantity);
	}
}
