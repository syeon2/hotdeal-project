package io.waterkite94.hd.hotdeal.item.dao.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.waterkite94.hd.hotdeal.item.domain.dto.FindAdminItemDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.FindItemDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.SearchItemListDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;

public interface ItemRepositoryCustom {

	Page<SearchItemListDto> searchItemsByCategoryId(Long categoryId, ItemType type, Pageable pageable);

	List<FindItemDto> searchItemsContainsWord(String word, Long itemOffset);

	FindItemDto findItemDetail(Long itemId);

	Page<FindAdminItemDto> findAdminItems(String memberId, Pageable pageable);
}
