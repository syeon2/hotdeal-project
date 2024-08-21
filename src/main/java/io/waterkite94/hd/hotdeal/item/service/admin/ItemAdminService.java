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
		AddItemServiceDto initializeDto = createInitializedDto(memberId, serviceDto);

		LocalDateTime preOrderSchedule = convertPreOrderSchedule(serviceDto);

		return itemRepository.save(itemMapper.toEntity(initializeDto, preOrderSchedule)).getId();
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
		return serviceDto.initialize(memberId, UuidUtil.createUuid());
	}

	private void validateMemberId(String memberId, String storedMemberId) {
		if (!storedMemberId.equals(memberId)) {
			throw new UnauthorizedMemberException("Unauthorized Member Id");
		}
	}

	private LocalDateTime convertPreOrderSchedule(AddItemServiceDto serviceDto) {
		ItemType itemType = serviceDto.getType();

		if (itemType.equals(PRE_ORDER) && !isBetweenNowTimeAndAfterSevenDate(serviceDto.getPreOrderSchedule())) {
			throw new IllegalArgumentException(
				"Pre order time can only be registered within 7 days from the current time");
		}

		return itemType.equals(PRE_ORDER) ? serviceDto.getPreOrderSchedule().toLocalDateTime() :
			LocalDateTime.now();
	}

	private boolean isBetweenNowTimeAndAfterSevenDate(PreOrderSchedule preOrderSchedule) {
		return preOrderSchedule.isBetweenStartTimeAndEndTime(
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
