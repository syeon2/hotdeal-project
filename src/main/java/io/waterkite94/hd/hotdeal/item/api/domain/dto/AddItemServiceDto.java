package io.waterkite94.hd.hotdeal.item.api.domain.dto;

import io.waterkite94.hd.hotdeal.item.api.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.api.domain.vo.ItemId;
import io.waterkite94.hd.hotdeal.item.api.domain.vo.ItemStatus;
import io.waterkite94.hd.hotdeal.item.api.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.item.api.domain.vo.PreOrderSchedule;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemServiceDto {

	private ItemId itemId;
	private String name;
	private Cost cost;
	private String introduction;
	private Integer quantity;
	private ItemType type;
	private PreOrderSchedule preOrderSchedule;
	private ItemStatus status;
	private String memberId;
	private Long categoryId;

	@Builder
	private AddItemServiceDto(ItemId itemId, String name, Cost cost, String introduction, Integer quantity,
		ItemType type, PreOrderSchedule preOrderSchedule, ItemStatus status, String memberId, Long categoryId) {
		this.itemId = itemId;
		this.name = name;
		this.cost = cost;
		this.introduction = introduction;
		this.quantity = quantity;
		this.type = type;
		this.preOrderSchedule = preOrderSchedule;
		this.status = status;
		this.memberId = memberId;
		this.categoryId = categoryId;
	}

	public AddItemServiceDto withQuantity(Integer quantity) {
		return new AddItemServiceDto(itemId, name, cost, introduction, quantity, type, preOrderSchedule, status,
			memberId, categoryId);
	}

	public AddItemServiceDto withUuid(String uuid) {
		ItemId createdItemId = ItemId.of(null, uuid);

		return new AddItemServiceDto(createdItemId, name, cost, introduction, quantity, type, preOrderSchedule, status,
			memberId, categoryId);
	}

	public AddItemServiceDto withMemberId(String memberId) {
		return new AddItemServiceDto(itemId, name, cost, introduction, quantity, type, preOrderSchedule, status,
			memberId, categoryId);
	}

	public AddItemServiceDto withStatus(ItemStatus status) {
		return new AddItemServiceDto(itemId, name, cost, introduction, quantity, type, preOrderSchedule, status,
			memberId, categoryId);
	}

}
