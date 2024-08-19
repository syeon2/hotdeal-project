package io.waterkite94.hd.hotdeal.item.service.admin;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.AddItemServiceDto;
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
		AddItemServiceDto addItemServiceDto = createAddItemServiceDto(10000, 1000);

		// when
		Long savedItemId = itemAdminService.addItemWithMemberId(memberId, addItemServiceDto);

		// then
		List<ItemEntity> findItems = itemRepository.findAll();
		assertThat(findItems).hasSize(1);
		assertThat(findItems.get(0).getId()).isEqualTo(savedItemId);
	}

	@Test
	@Transactional
	@DisplayName(value = "할인금액이 정상가보다 높다면 예외를 반환합니다.")
	void addItemWithMember_discountOtPrice() {
		// given
		String memberId = "memberId";
		int price = 10000;
		int discount = 1000000;

		AddItemServiceDto addItemServiceDto = createAddItemServiceDto(price, discount);

		// when // then
		assertThatThrownBy(() -> itemAdminService.addItemWithMemberId(memberId, addItemServiceDto))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("할인 금액은 정상 금액보다 낮아야합니다.");
	}

	@Test
	@Transactional
	@DisplayName(value = "상품을 비활성화합니다.")
	void changeItemStatusInactive() {
		// given
		String memberId = "memberId";
		AddItemServiceDto addItemServiceDto = createAddItemServiceDto(10000, 1000);
		Long savedItemId = itemAdminService.addItemWithMemberId(memberId, addItemServiceDto);

		// when
		itemAdminService.changeItemStatusInactive(memberId, savedItemId);

		// then
		Optional<ItemEntity> findItemOptional = itemRepository.findById(savedItemId);
		assertThat(findItemOptional).isPresent();
		assertThat(findItemOptional.get().getStatus()).isEqualTo(ItemStatus.INACTIVE);
	}

	private AddItemServiceDto createAddItemServiceDto(int price, int discount) {
		return AddItemServiceDto.builder()
			.name("name")
			.cost(Cost.builder().price(price).discount(discount).build())
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
