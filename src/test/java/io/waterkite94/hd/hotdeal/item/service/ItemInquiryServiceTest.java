package io.waterkite94.hd.hotdeal.item.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.item.dao.ItemInquiryRepository;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemInquiryEntity;
import io.waterkite94.hd.hotdeal.item.domain.ItemInquiry;
import io.waterkite94.hd.hotdeal.item.service.normal.ItemInquiryService;

class ItemInquiryServiceTest extends IntegrationTestSupport {

	@Autowired
	private ItemInquiryService itemInquiryService;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemInquiryRepository itemInquiryRepository;

	@BeforeEach
	void before() {
		itemRepository.deleteAllInBatch();
		itemInquiryRepository.deleteAllInBatch();
	}

	@Test
	// @Transactional
	@DisplayName(value = "")
	void addItemInquiry() {
		// given
		ItemEntity itemEntity = createItemEntity();
		ItemEntity savedItemEntity = itemRepository.save(itemEntity);

		ItemInquiry itemInquiry = createItemInquiryDomain(savedItemEntity);

		// when
		itemInquiryService.addItemInquiry(itemInquiry);

		// then
		List<ItemInquiryEntity> findItemInquiries = itemInquiryRepository.findAll();
		assertThat(findItemInquiries).hasSize(1);
	}

	private static ItemInquiry createItemInquiryDomain(ItemEntity savedItemEntity) {
		ItemInquiry itemInquiry = ItemInquiry.builder()
			.comment("comment1")
			.itemId(savedItemEntity.getId())
			.build();
		return itemInquiry;
	}

	private ItemEntity createItemEntity() {
		return null;
	}
}
