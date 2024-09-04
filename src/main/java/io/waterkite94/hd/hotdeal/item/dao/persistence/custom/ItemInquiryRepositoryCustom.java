package io.waterkite94.hd.hotdeal.item.dao.persistence.custom;

import java.util.List;

import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemInquiriesDto;

public interface ItemInquiryRepositoryCustom {

	List<RetrieveItemInquiriesDto> findItemInquiriesByItemId(Long itemId, Long offset);

	List<Long> findItemInquiriesForToday(String memberId, Long itemId);
}
