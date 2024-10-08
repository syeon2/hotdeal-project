package io.waterkite94.hd.hotdeal.item.api.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.vo.CostResponse;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.vo.PreOrderScheduleResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RetrieveItemsResponse {

	@JsonProperty(value = "item_id")
	private Long itemId;

	@JsonProperty(value = "item_name")
	private String itemName;

	@JsonProperty("cost")
	private CostResponse cost;

	@JsonProperty("is_pre_order_item")
	private Boolean isPreOrderItem;

	@JsonProperty("quantity")
	private Integer quantity;

	@JsonProperty("pre_order_schedule")
	private PreOrderScheduleResponse preOrderSchedule;

	@JsonProperty("seller_id")
	private String sellerId;

	@JsonProperty("seller_name")
	private String sellerName;

	@Builder
	private RetrieveItemsResponse(Long itemId, String itemName, CostResponse cost, Integer quantity,
		Boolean isPreOrderItem, PreOrderScheduleResponse preOrderSchedule, String sellerId, String sellerName) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.cost = cost;
		this.quantity = quantity;
		this.isPreOrderItem = isPreOrderItem;
		this.preOrderSchedule = preOrderSchedule;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
	}

	public static RetrieveItemsResponse of(RetrieveItemsDto retrieveItemsDto) {
		return RetrieveItemsResponse.builder()
			.itemId(retrieveItemsDto.getItemId())
			.itemName(retrieveItemsDto.getItemName())
			.cost(CostResponse.of(retrieveItemsDto.getCost()))
			.quantity(retrieveItemsDto.getQuantity())
			.isPreOrderItem(retrieveItemsDto.getIsPreOrderItem())
			.preOrderSchedule(PreOrderScheduleResponse.fromLocalDateTime(
				retrieveItemsDto.getPreOrderTime()))
			.sellerId(retrieveItemsDto.getSellerId())
			.sellerName(retrieveItemsDto.getSellerName())
			.build();
	}

}
