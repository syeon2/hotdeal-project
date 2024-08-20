package io.waterkite94.hd.hotdeal.item.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.domain.dto.SearchItemListDto;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.CostRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.PreOrderScheduleRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchItemBoardResponse {

	@JsonProperty(value = "item_id")
	private Long itemId;

	@JsonProperty(value = "item_name")
	private String itemName;

	@JsonProperty("cost")
	private CostRequest cost;

	@JsonProperty("is_pre_order_item")
	private Boolean isPreOrderItem;

	@JsonProperty("pre_order_schedule")
	private PreOrderScheduleRequest preOrderSchedule;

	@JsonProperty("seller_id")
	private String sellerId;

	@JsonProperty("seller_name")
	private String sellerName;

	@Builder
	private SearchItemBoardResponse(Long itemId, String itemName, CostRequest cost, Boolean isPreOrderItem,
		PreOrderScheduleRequest preOrderSchedule, String sellerId, String sellerName) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.cost = cost;
		this.isPreOrderItem = isPreOrderItem;
		this.preOrderSchedule = preOrderSchedule;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
	}

	public static SearchItemBoardResponse of(SearchItemListDto searchItemListDto) {
		return SearchItemBoardResponse.builder()
			.itemId(searchItemListDto.getItemId())
			.itemName(searchItemListDto.getItemName())
			.cost(CostRequest.builder()
				.price(searchItemListDto.getPrice())
				.discount(searchItemListDto.getDiscount())
				.build()
			)
			.isPreOrderItem(searchItemListDto.getIsPreOrderItem())
			.preOrderSchedule(PreOrderScheduleRequest.fromLocalDateTime(
				searchItemListDto.getPreOrderTime()))
			.sellerId(searchItemListDto.getSellerId())
			.sellerName(searchItemListDto.getSellerName())
			.build();
	}

}
