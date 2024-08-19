package io.waterkite94.hd.hotdeal.item.domain;

import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemId;
import io.waterkite94.hd.hotdeal.item.domain.vo.PreOrderSchedule;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemServiceDto {

	private ItemId itemId;
	private String name;
	private Cost cost;
	private String introduction;
	private ItemType type;
	private PreOrderSchedule preOrderSchedule;
	private String memberId;
	private Long categoryId;

	@Builder
	private AddItemServiceDto(ItemId itemId, String name, Cost cost, String introduction, ItemType type,
		PreOrderSchedule preOrderSchedule, String memberId, Long categoryId) {
		this.itemId = itemId;
		this.name = name;
		this.cost = cost;
		this.introduction = introduction;
		this.type = type;
		this.preOrderSchedule = preOrderSchedule;
		this.memberId = memberId;
		this.categoryId = categoryId;
	}

	public AddItemServiceDto initialize(String memberId, String createdUuid) {
		if (!cost.isCorrectCost()) {
			throw new IllegalArgumentException("할인 금액은 정상 금액보다 낮아야합니다.");
		}

		return this
			.withUuid(createdUuid)
			.withMemberId(memberId);
	}

	private AddItemServiceDto withUuid(String uuid) {
		ItemId createdItemId = ItemId.of(null, uuid);

		return new AddItemServiceDto(createdItemId, name, cost, introduction, type, preOrderSchedule, memberId,
			categoryId);
	}

	private AddItemServiceDto withMemberId(String memberId) {
		return new AddItemServiceDto(itemId, name, cost, introduction, type, preOrderSchedule, memberId, categoryId);
	}

}
