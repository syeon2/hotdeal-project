package io.waterkite94.hd.hotdeal.item.service.admin;

import static io.waterkite94.hd.hotdeal.item.domain.ItemType.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.item.dao.ItemMapper;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.domain.AddItemServiceDto;
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

	public void deleteItem(String uuid) {
		itemRepository.deleteByUuid(uuid);
	}

	private static String createUuid() {
		return UUID.randomUUID().toString();
	}

	private static LocalDateTime convertedPreOrderSchedule(AddItemServiceDto serviceDto) {
		return serviceDto.getType().equals(PRE_ORDER) ? serviceDto.getPreOrderSchedule().toLocalDateTime() :
			LocalDateTime.now();
	}
}
