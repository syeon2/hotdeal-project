package io.waterkite94.hd.hotdeal.item.dao.custom;

import java.util.List;

import io.waterkite94.hd.hotdeal.item.domain.dto.ItemBoardDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;

public interface ItemRepositoryCustom {

	List<ItemBoardDto> searchItemsByCategoryId(Long categoryId, ItemType itemType, Long itemOffset);

	List<ItemBoardDto> searchItemsContainsWord(String word, Long itemOffset);

	ItemBoardDto findItemDetail(Long itemId);
}
