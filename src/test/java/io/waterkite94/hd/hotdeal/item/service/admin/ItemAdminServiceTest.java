package io.waterkite94.hd.hotdeal.item.service.admin;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

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
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.AddItemServiceDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.ChangeItemInfoDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveRegisteredItemDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemStatus;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.item.domain.vo.PreOrderSchedule;

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
	void addItemWithMemberId() {
		// given
		String memberId = "memberId";
		AddItemServiceDto addItemServiceDto = createAddItemServiceDto();

		// when
		Long savedItemId = itemAdminService.addItemWithMemberId(memberId, addItemServiceDto);

		// then
		List<ItemEntity> findItems = itemRepository.findAll();
		assertThat(findItems).hasSize(1);
		assertThat(findItems.get(0).getId()).isEqualTo(savedItemId);
	}

	@Test
	@Transactional
	@DisplayName(value = "상품을 비활성화합니다.")
	void changeItemStatusInactive() {
		// given
		String memberId = "memberId";
		AddItemServiceDto addItemServiceDto = createAddItemServiceDto();
		Long savedItemId = itemAdminService.addItemWithMemberId(memberId, addItemServiceDto);

		// when
		itemAdminService.changeItemStatusInactive(memberId, savedItemId);

		// then
		Optional<ItemEntity> findItemOptional = itemRepository.findById(savedItemId);
		assertThat(findItemOptional).isPresent();
		assertThat(findItemOptional.get().getStatus()).isEqualTo(ItemStatus.INACTIVE);
	}

	@Test
	@Transactional
	@DisplayName(value = "회원이 등록한 상품을 조회합니다.")
	void findAdminItems() {
		// given
		String memberId = "memberId";
		AddItemServiceDto addItemServiceDto = createAddItemServiceDto();
		Long savedItemId = itemAdminService.addItemWithMemberId(memberId, addItemServiceDto);

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "createdAt"));

		// when
		Page<RetrieveRegisteredItemDto> findItems = itemAdminService.findAdminItems(memberId, pageRequest);

		// then
		assertThat(findItems.getContent()).hasSize(1);
		assertThat(findItems.getTotalElements()).isEqualTo(1);
		assertThat(findItems.getContent().get(0).getItemId()).isEqualTo(savedItemId);
	}

	@Test
	@Transactional
	@DisplayName(value = "상품의 정보를 변경합니다.")
	void changeItemInfo() {
		// given
		String memberId = "memberId";
		AddItemServiceDto addItemServiceDto = createAddItemServiceDto();
		Long savedItemId = itemAdminService.addItemWithMemberId(memberId, addItemServiceDto);

		ChangeItemInfoDto changeItemInfoDto = createChangeItemInfoDto();

		// when
		itemAdminService.changeItemInfo(memberId, savedItemId, changeItemInfoDto);

		// then
		Optional<ItemEntity> findItemOptional = itemRepository.findById(savedItemId);
		assertThat(findItemOptional).isPresent();
		assertThat(findItemOptional.get()).extracting("name", "introduction")
			.containsExactlyInAnyOrder(changeItemInfoDto.getName(), changeItemInfoDto.getIntroduction());
	}

	private ChangeItemInfoDto createChangeItemInfoDto() {
		return ChangeItemInfoDto.builder()
			.name("changeName")
			.introduction("changeIntroduction")
			.cost(Cost.of(10000, 1000))
			.preOrderSchedule(PreOrderSchedule.of(2024, 10, 10, 10, 10))
			.categoryId(1L)
			.build();
	}

	private AddItemServiceDto createAddItemServiceDto() {
		return AddItemServiceDto.builder()
			.name("name")
			.cost(Cost.builder().price(10000).discount(1000).build())
			.introduction("introduction")
			.type(ItemType.PRE_ORDER)
			.preOrderSchedule(PreOrderSchedule.builder()
				.year(2024)
				.month(10)
				.date(15)
				.hour(20)
				.minute(25)
				.build())
			.categoryId(1L)
			.build();
	}
}
