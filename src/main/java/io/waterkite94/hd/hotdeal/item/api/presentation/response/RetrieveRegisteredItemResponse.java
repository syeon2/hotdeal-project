package io.waterkite94.hd.hotdeal.item.api.presentation.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.RetrieveRegisteredItemDto;
import io.waterkite94.hd.hotdeal.item.api.presentation.request.ItemTypeRequest;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.vo.CostResponse;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.vo.PreOrderScheduleResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RetrieveRegisteredItemResponse {

	@JsonProperty("item_id")
	private Long itemId;

	@JsonProperty("item_uuid")
	private String uuid;

	@JsonProperty("name")
	private String name;

	@JsonProperty("cost")
	private CostResponse cost;

	@JsonProperty("item_type")
	private ItemTypeRequest itemType;

	@JsonProperty("pre_order_schedule")
	private PreOrderScheduleResponse preOrderSchedule;

	@JsonProperty("quantity")
	private Integer quantity;

	@JsonProperty("created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime createdAt;

	@JsonProperty("category_id")
	private Long categoryId;

	@JsonProperty("category_name")
	private String categoryName;

	@Builder
	private RetrieveRegisteredItemResponse(Long itemId, String uuid, String name, CostResponse cost,
		ItemTypeRequest itemType, PreOrderScheduleResponse preOrderSchedule, Integer quantity, LocalDateTime createdAt,
		Long categoryId, String categoryName) {
		this.itemId = itemId;
		this.uuid = uuid;
		this.name = name;
		this.cost = cost;
		this.itemType = itemType;
		this.preOrderSchedule = preOrderSchedule;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public static RetrieveRegisteredItemResponse of(RetrieveRegisteredItemDto itemDto) {
		return RetrieveRegisteredItemResponse.builder()
			.itemId(itemDto.getItemId().getId())
			.uuid(itemDto.getItemId().getUuid())
			.name(itemDto.getItemName())
			.cost(CostResponse.of(itemDto.getCost()))
			.itemType(ItemTypeRequest.from(itemDto.getItemType().name()))
			.preOrderSchedule(PreOrderScheduleResponse.fromLocalDateTime(itemDto.getPreOrderSchedule()))
			.quantity(itemDto.getQuantity())
			.createdAt(itemDto.getCreatedAt())
			.categoryId(itemDto.getCategoryId())
			.categoryName(itemDto.getCategoryName())
			.build();
	}
}
