package io.waterkite94.hd.hotdeal.item.dao.custom;

import java.util.List;

import io.waterkite94.hd.hotdeal.item.domain.dto.ItemInquiryBoardDto;

public interface ItemInquiryRepositoryCustom {

	List<ItemInquiryBoardDto> findItemInquiries(Long itemId, Long offset);

	List<Long> findItemInquiriesForToday(String memberId, Long itemId);
}
