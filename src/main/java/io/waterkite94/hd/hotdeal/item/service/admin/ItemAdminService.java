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
				initializeDto(memberId, serviceDto),
				convertPreOrderSchedule(serviceDto)
			)
		);

		return savedItem.getId();
	}

	@Transactional
	public void changeItemStatusInactive(String memberId, Long itemId) {
		ItemEntity findItem = findItemWithValidation(itemId);

		validateMemberId(memberId, findItem.getMemberId());

		findItem.changeStatusInactive();
	}

	@Transactional(readOnly = true)
	public Page<RetrieveRegisteredItemDto> findAdminItems(String memberId, Pageable pageable) {
		return itemRepository.findAdminItemsByMemberId(memberId, pageable);
	}

	@Transactional
	public void changeItemInfo(String memberId, Long itemId, ChangeItemInfoDto changeItemInfoDto) {
		ItemEntity findItem = findItemWithValidation(itemId);

		validatePreOrderScheduleAfterCreation(changeItemInfoDto, findItem);
		validateMemberId(memberId, findItem.getMemberId());

		changeItemInfoDto.changeInfo(findItem);
	}

	private void validatePreOrderSchedule(AddItemServiceDto serviceDto) {
		if (serviceDto.getType().equals(PRE_ORDER)
			&& isNotWithinNextSevenDays(serviceDto.getPreOrderSchedule())
		) {
			throw new IllegalArgumentException(
				"예약 구매 상품은 '현재 시간으로부터 2시간 이후' ~ 현재 시간으로부터 7일 이후' 사이의 시간으로 설정 가능합니다.");
		}
	}

	private static AddItemServiceDto initializeDto(String memberId, AddItemServiceDto serviceDto) {
		if (serviceDto.getType().equals(ItemType.NORMAL_ORDER)) {
			serviceDto = serviceDto.withQuantity(0);
		}

		return serviceDto
			.withUuid(UuidUtil.createUuid())
			.withStatus(ItemStatus.ACTIVE)
			.withMemberId(memberId);
	}

	private LocalDateTime convertPreOrderSchedule(AddItemServiceDto serviceDto) {
		return serviceDto.getType().equals(PRE_ORDER) ? serviceDto.getPreOrderSchedule().toLocalDateTime() :
			LocalDateTime.now();
	}

	private ItemEntity findItemWithValidation(Long itemId) {
		return itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("상품 아이디에 해당하는 상품을 찾을 수 없습니다."));
	}

	private void validateMemberId(String memberId, String storedMemberId) {
		if (!storedMemberId.equals(memberId)) {
			throw new UnauthorizedMemberException("상품을 등록한 회원과 입력받은 회원의 아이디가 일치하지 않습니다.");
		}
	}

	private void validatePreOrderScheduleAfterCreation(ChangeItemInfoDto changeItemInfoDto, ItemEntity findItem) {
		if (findItem.getType().equals(PRE_ORDER)
			&& isNotWithinSevenDaysAfterCreation(changeItemInfoDto.getPreOrderSchedule(), findItem.getCreatedAt())
		) {
			throw new IllegalArgumentException("예약 구매 시간은 상품 등록 시간으로부터 7일 이내의 시간만 등록이 가능합니다.");
		}
	}

	private boolean isNotWithinNextSevenDays(PreOrderSchedule preOrderSchedule) {
		return !preOrderSchedule.isBetweenStartTimeAndEndTime(
			LocalDateTime.now().plusHours(2),
			LocalDateTime.now().plusDays(7).toLocalDate()
		);
	}

	private boolean isNotWithinSevenDaysAfterCreation(PreOrderSchedule preOrderSchedule,
		LocalDateTime createdDate) {
		return !preOrderSchedule.isBetweenStartTimeAndEndTime(LocalDateTime.now().plusHours(2),
			createdDate.plusDays(7).toLocalDate());
	}

}
