package io.waterkite94.hd.hotdeal.item.web.api.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.domain.dto.FindAdminItemDto;
import io.waterkite94.hd.hotdeal.item.web.api.request.ItemTypeRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.CostRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.PreOrderScheduleRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindAdminItemResponse {

	@JsonProperty("item_id")
	private Long itemId;

	@JsonProperty("item_uuid")
	private String uuid;

	@JsonProperty("name")
	private String name;

	@JsonProperty("cost")
	private CostRequest cost;

	@JsonProperty("item_type")
	private ItemTypeRequest itemType;

	@JsonProperty("pre_order_schedule")
	private PreOrderScheduleRequest preOrderSchedule;

	@JsonProperty("created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime createdAt;

	@JsonProperty("category_id")
	private Long categoryId;

	@JsonProperty("category_name")
	private String categoryName;

	@Builder
	private FindAdminItemResponse(Long itemId, String uuid, String name, CostRequest cost, ItemTypeRequest itemType,
		PreOrderScheduleRequest preOrderSchedule, LocalDateTime createdAt, Long categoryId, String categoryName) {
		this.itemId = itemId;
		this.uuid = uuid;
		this.name = name;
		this.cost = cost;
		this.itemType = itemType;
		this.preOrderSchedule = preOrderSchedule;
		this.createdAt = createdAt;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public static FindAdminItemResponse of(FindAdminItemDto itemDto) {
		return FindAdminItemResponse.builder()
			.itemId(itemDto.getItemId())
			.uuid(itemDto.getItemUuid())
			.name(itemDto.getItemName())
			.cost(CostRequest.builder()
				.price(itemDto.getPrice())
				.discount(itemDto.getDiscount())
				.build()
			)
			.itemType(ItemTypeRequest.from(itemDto.getItemType().name()))
			.preOrderSchedule(
				PreOrderScheduleRequest.convertLocalDateTimeToPreOrderScheduleRequest(itemDto.getPreOrderSchedule()))
			.createdAt(itemDto.getCreatedAt())
			.categoryId(itemDto.getCategoryId())
			.categoryName(itemDto.getCategoryName())
			.build();
	}
}
