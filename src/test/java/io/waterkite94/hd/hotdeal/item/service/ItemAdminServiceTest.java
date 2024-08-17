package io.waterkite94.hd.hotdeal.item.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.Item;
import io.waterkite94.hd.hotdeal.item.domain.ItemType;

class ItemAdminServiceTest extends IntegrationTestSupport {

	@Autowired
	private ItemAdminService itemAdminService;

	@Autowired
	private ItemRepository itemRepository;

	@BeforeEach
	void before() {
		itemRepository.deleteAllInBatch();
	}

	@Test
	@Transactional
	@DisplayName(value = "상품을 추가합니다.")
	void addItem() {
		// given
		Item item = createDomain();

		// when
		String savedItem = itemAdminService.addItem(item);

		// then
		List<ItemEntity> findItems = itemRepository.findAll();
		assertThat(findItems).hasSize(1);
		assertThat(findItems.get(0).getItemId()).isEqualTo(savedItem);
	}

	@Test
	@Transactional
	@DisplayName(value = "상품을 삭제합니다.")
	void deleteItem() {
		// given
		Item item = createDomain();
		String savedItem = itemAdminService.addItem(item);

		// when
		itemAdminService.deleteItem(savedItem);

		// then
		assertThat(itemRepository.findAll()).hasSize(0);
	}

	private Item createDomain() {
		return Item.builder()
			.name("name")
			.price(10000)
			.discount(0)
			.introduction("introduction")
			.type(ItemType.PRE_ORDER)
			.memberId("memberId")
			.categoryId(1L)
			.build();
	}
}
