package io.waterkite94.hd.hotdeal.item.service.admin;

import java.util.UUID;

import org.springframework.stereotype.Service;

import io.waterkite94.hd.hotdeal.item.dao.ItemMapper;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.Item;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemAdminService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	public String addItem(String memberId, Item item) {
		ItemEntity savedItem = itemRepository.save(
			itemMapper.toEntity(item.initialize(memberId, createUuid()))
		);

		return savedItem.getItemId();
	}

	public void deleteItem(String itemId) {
		itemRepository.deleteByItemId(itemId);
	}

	private static String createUuid() {
		return UUID.randomUUID().toString();
	}
}
