package io.waterkite94.hd.hotdeal.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.item.dao.ItemInquiryMapper;
import io.waterkite94.hd.hotdeal.item.dao.ItemInquiryRepository;
import io.waterkite94.hd.hotdeal.item.dao.ItemRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemInquiryEntity;
import io.waterkite94.hd.hotdeal.item.domain.ItemInquiry;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemInquiryService {

	private final ItemInquiryRepository itemInquiryRepository;
	private final ItemRepository itemRepository;
	private final ItemInquiryMapper itemInquiryMapper;

	@Transactional
	public Long addItemInquiry(ItemInquiry itemInquiry) {
		ItemEntity itemProxyEntity = itemRepository.getReferenceById(itemInquiry.getItemId());

		ItemInquiryEntity savedItemInquiry = itemInquiryRepository.save(
			itemInquiryMapper.toEntity(itemInquiry, itemProxyEntity)
		);

		return savedItemInquiry.getId();
	}
}
