package io.waterkite94.hd.hotdeal.item.service.normal;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.domain.dto.AddItemServiceDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.item.domain.vo.PreOrderSchedule;
import io.waterkite94.hd.hotdeal.item.service.admin.ItemAdminService;

class ItemServiceTest extends IntegrationTestSupport {

	@Autowired
	private ItemService itemService;

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
	@DisplayName(value = "필터를 통한 상품들을 조회합니다.")
	void findItems() {
		// given
		Long categoryId = 1L;
		ItemType itemType = ItemType.PRE_ORDER;
		AddItemServiceDto itemDto = createAddItemServiceDto(categoryId, itemType);
		Long savedItemId = itemAdminService.addItemWithMemberId("memberId", itemDto);
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("createdAt"));

		// when
		Page<RetrieveItemsDto> findItemsWithPage = itemService.findItems(categoryId, itemType, null, pageRequest);

		// then
		assertThat(findItemsWithPage.getContent().get(0).getItemId()).isEqualTo(savedItemId);
	}

	private AddItemServiceDto createAddItemServiceDto(Long categoryId, ItemType itemType) {
		return AddItemServiceDto.builder()
			.name("name")
			.cost(Cost.builder().price(10000).discount(1000).build())
			.introduction("introduction")
			.type(itemType)
			.preOrderSchedule(PreOrderSchedule.builder()
				.year(2024)
				.month(10)
				.date(15)
				.hour(20)
				.minute(25)
				.build())
			.categoryId(categoryId)
			.build();
	}
}
