package io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.custom;

import java.util.List;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.RetrieveItemInquiriesDto;

public interface ItemInquiryRepositoryCustom {

	List<RetrieveItemInquiriesDto> findItemInquiriesByItemId(Long itemId, Long offset);

	List<Long> findItemInquiriesForToday(String memberId, Long itemId);
}
