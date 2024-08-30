package io.waterkite94.hd.hotdeal.item.service.admin;

import static io.waterkite94.hd.hotdeal.item.domain.vo.ItemType.*;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.UnauthorizedMemberException;
import io.waterkite94.hd.hotdeal.common.util.UuidUtil;
import io.waterkite94.hd.hotdeal.item.dao.ItemMapper;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.AddItemServiceDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.ChangeItemInfoDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveRegisteredItemDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemStatus;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.item.domain.vo.PreOrderSchedule;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemAdminService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	@Transactional
	public Long addItemWithMemberId(String memberId, AddItemServiceDto serviceDto) {
		validatePreOrderSchedule(serviceDto);

		ItemEntity savedItem = itemRepository.save(
			itemMapper.toEntity(
				createInitializedDto(memberId, serviceDto),
				convertPreOrderSchedule(serviceDto)
			)
		);

		return savedItem.getId();
	}

	@Transactional
	public void changeItemStatusInactive(String memberId, Long itemId) {
		ItemEntity findItem = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("Item not found"));

		validateMemberId(memberId, findItem.getMemberId());

		findItem.changeStatusInactive();
	}

	@Transactional
	public Page<RetrieveRegisteredItemDto> findAdminItems(String memberId, Pageable pageable) {
		return itemRepository.findAdminItemsByMemberId(memberId, pageable);
	}

	@Transactional
	public void changeItemInfo(String memberId, Long itemId, ChangeItemInfoDto changeItemInfoDto) {
		ItemEntity findItem = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("Item not found"));

		ItemType itemType = findItem.getType();
		if (itemType.equals(PRE_ORDER)
			&& !isBetweenNowTimeAndAfterCreateDateForSevenDate(
			changeItemInfoDto.getPreOrderSchedule(),
			findItem.getCreatedAt())
		) {
			throw new IllegalArgumentException(
				"Pre order time can only be registered within 7 days from the create item time");
		}

		validateMemberId(memberId, findItem.getMemberId());

		changeItemInfoDto.changeInfo(findItem);
	}

	private static AddItemServiceDto createInitializedDto(String memberId, AddItemServiceDto serviceDto) {
		if (serviceDto.getType().equals(ItemType.NORMAL_ORDER)) {
			serviceDto = serviceDto.withQuantity(0);
		}

		return serviceDto
			.withUuid(UuidUtil.createUuid())
			.withStatus(ItemStatus.ACTIVE)
			.withMemberId(memberId);
	}

	private void validateMemberId(String memberId, String storedMemberId) {
		if (!storedMemberId.equals(memberId)) {
			throw new UnauthorizedMemberException("Unauthorized Member Id");
		}
	}

	private LocalDateTime convertPreOrderSchedule(AddItemServiceDto serviceDto) {
		return serviceDto.getType().equals(PRE_ORDER) ? serviceDto.getPreOrderSchedule().toLocalDateTime() :
			LocalDateTime.now();
	}

	private void validatePreOrderSchedule(AddItemServiceDto serviceDto) {
		if (serviceDto.getType().equals(PRE_ORDER)
			&& isNotBetweenNowTimeAndAfterSevenDate(serviceDto.getPreOrderSchedule())
		) {
			throw new IllegalArgumentException(
				"예약 구매 상품은 '현재 시간으로부터 2시간 이후' ~ 현재 시간으로부터 7일 이후' 사이의 시간으로 설정 가능합니다.");
		}
	}

	private boolean isNotBetweenNowTimeAndAfterSevenDate(PreOrderSchedule preOrderSchedule) {
		return !preOrderSchedule.isBetweenStartTimeAndEndTime(
			LocalDateTime.now().plusHours(2),
			LocalDateTime.now().plusDays(7).toLocalDate()
		);
	}

	private boolean isBetweenNowTimeAndAfterCreateDateForSevenDate(PreOrderSchedule preOrderSchedule,
		LocalDateTime createdDate) {
		return preOrderSchedule.isBetweenStartTimeAndEndTime(LocalDateTime.now().plusHours(2),
			createdDate.plusDays(7).toLocalDate());
	}

}
