package io.waterkite94.hd.hotdeal.item.dao.custom;

import java.util.List;

import io.waterkite94.hd.hotdeal.item.domain.ItemType;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemBoardDto;

public interface ItemRepositoryCustom {

	List<ItemBoardDto> searchItemsByCategoryId(Long categoryId, ItemType itemType, Long itemOffset);

	List<ItemBoardDto> searchItemsContainsWord(String word, Long itemOffset);
}
