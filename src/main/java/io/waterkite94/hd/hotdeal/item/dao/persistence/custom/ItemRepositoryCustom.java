package io.waterkite94.hd.hotdeal.item.dao.persistence.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.waterkite94.hd.hotdeal.item.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveRegisteredItemDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;

public interface ItemRepositoryCustom {

	Page<RetrieveRegisteredItemDto> findAdminItemsByMemberId(String memberId, Pageable pageable);

	Page<RetrieveItemsDto> searchItemsByCategoryId(Long categoryId, ItemType type, String search, Pageable pageable);

	ItemDetailDto findItemDetail(Long itemId);
}
