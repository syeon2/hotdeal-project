package io.waterkite94.hd.hotdeal.item.api.domain.dto;

import io.waterkite94.hd.hotdeal.item.api.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.api.domain.vo.PreOrderSchedule;
import io.waterkite94.hd.hotdeal.item.api.infrastructure.persistence.entity.ItemEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangeItemInfoDto {

	private String name;
	private String introduction;
	private Cost cost;
	private PreOrderSchedule preOrderSchedule;
	private Long categoryId;

	@Builder
	private ChangeItemInfoDto(String name, String introduction, Cost cost, PreOrderSchedule preOrderSchedule,
		Long categoryId) {
		this.name = name;
		this.introduction = introduction;
		this.cost = cost;
		this.preOrderSchedule = preOrderSchedule;
		this.categoryId = categoryId;
	}

	public void changeInfo(ItemEntity itemEntity) {
		itemEntity.changeName(name);
		itemEntity.changeIntroduction(introduction);
		itemEntity.changePrice(cost.getPrice());
		itemEntity.changeDiscount(cost.getDiscount());
		itemEntity.changePreOrderTime(preOrderSchedule.toLocalDateTime());
		itemEntity.changeCategoryId(categoryId);
	}
}
