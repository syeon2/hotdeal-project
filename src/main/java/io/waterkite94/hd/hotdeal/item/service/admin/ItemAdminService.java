package io.waterkite94.hd.hotdeal.item.service.admin;

import static io.waterkite94.hd.hotdeal.item.domain.vo.ItemType.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.item.dao.ItemMapper;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.AddItemServiceDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.FindAdminItemDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemAdminService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	@Transactional
	public Long addItemWithMemberId(String memberId, AddItemServiceDto serviceDto) {
		AddItemServiceDto initializeDto = serviceDto.initialize(memberId, createUuid());

		LocalDateTime preOrderSchedule = convertedPreOrderSchedule(serviceDto);

		return itemRepository.save(itemMapper.toEntity(initializeDto, preOrderSchedule)).getId();
	}

	@Transactional
	public void changeItemStatusInactive(String memberId, Long itemId) {
		ItemEntity findItem = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("item not found"));

		verificationMemberId(memberId, findItem.getMemberId());

		findItem.changeStatusInactive();
	}

	@Transactional
	public Page<FindAdminItemDto> findAdminItems(String memberId, Pageable pageable) {
		return itemRepository.findAdminItems(memberId, pageable);
	}

	private void verificationMemberId(String memberId, String storedMemberId) {
		if (!storedMemberId.equals(memberId)) {
			throw new IllegalArgumentException("Unauthorized member Id");
		}
	}

	private static String createUuid() {
		return UUID.randomUUID().toString();
	}

	private static LocalDateTime convertedPreOrderSchedule(AddItemServiceDto serviceDto) {
		return serviceDto.getType().equals(PRE_ORDER) ? serviceDto.getPreOrderSchedule().toLocalDateTime() :
			LocalDateTime.now();
	}
}
