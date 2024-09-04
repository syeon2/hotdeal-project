package io.waterkite94.hd.hotdeal.item.service.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.TooManyRequestException;
import io.waterkite94.hd.hotdeal.common.error.exception.UnauthorizedMemberException;
import io.waterkite94.hd.hotdeal.item.dao.persistence.ItemInquiryMapper;
import io.waterkite94.hd.hotdeal.item.dao.persistence.ItemInquiryRepository;
import io.waterkite94.hd.hotdeal.item.dao.persistence.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.persistence.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.dao.persistence.entity.ItemInquiryEntity;
import io.waterkite94.hd.hotdeal.item.domain.ItemInquiry;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemInquiriesDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemInquiryService {

	private final ItemInquiryRepository itemInquiryRepository;
	private final ItemRepository itemRepository;
	private final ItemInquiryMapper itemInquiryMapper;

	@Transactional
	public Long addItemInquiry(ItemInquiry itemInquiry) {
		checkMaximumAddedInquiries(itemInquiry);

		ItemEntity itemProxyEntity = itemRepository.getReferenceById(itemInquiry.getItemId());

		ItemInquiryEntity savedItemInquiry = itemInquiryRepository.save(
			itemInquiryMapper.toEntity(itemInquiry, itemProxyEntity));

		return savedItemInquiry.getId();
	}

	@Transactional
	public void deleteItemInquiry(Long itemInquiryId, String memberId) {
		ItemInquiryEntity findItemInquiry = itemInquiryRepository.findById(itemInquiryId).orElseThrow(() ->
			new IllegalArgumentException("ItemInquiry not found"));

		if (!findItemInquiry.getMemberId().equals(memberId)
			&& !findItemInquiry.getItem().getMemberId().equals(memberId)
		) {
			throw new UnauthorizedMemberException("Unauthorized Member Id");
		}

		itemInquiryRepository.deleteById(itemInquiryId);
	}

	@Transactional
	public List<RetrieveItemInquiriesDto> findItemInquiries(Long itemId, Long offset) {
		return itemInquiryRepository.findItemInquiriesByItemId(itemId, offset);
	}

	private void checkMaximumAddedInquiries(ItemInquiry itemInquiry) {
		List<Long> itemInquiriesForToday = itemInquiryRepository.findItemInquiriesForToday(
			itemInquiry.getMemberId(),
			itemInquiry.getItemId()
		);

		if (itemInquiriesForToday.size() >= 3) {
			throw new TooManyRequestException("You have reached the maximum number of orders you can save today.");
		}
	}
}
