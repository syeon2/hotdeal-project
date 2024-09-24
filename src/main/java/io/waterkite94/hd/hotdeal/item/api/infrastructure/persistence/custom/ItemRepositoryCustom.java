package io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.RetrieveRegisteredItemDto;
import io.waterkite94.hd.hotdeal.item.api.domain.vo.ItemType;

public interface ItemRepositoryCustom {

	Page<RetrieveRegisteredItemDto> findAdminItemsByMemberId(String memberId, Pageable pageable);

	Page<RetrieveItemsDto> searchItemsByCategoryId(Long categoryId, ItemType type, String search, Pageable pageable);

	ItemDetailDto findItemDetail(Long itemId);
}
