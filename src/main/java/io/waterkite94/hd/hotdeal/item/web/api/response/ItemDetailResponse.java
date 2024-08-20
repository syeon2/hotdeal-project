package io.waterkite94.hd.hotdeal.item.web.api.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.CostRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.PreOrderScheduleRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemDetailResponse {

	@JsonProperty("item_id")
	private Long itemId;

	@JsonProperty("item_name")
	private String itemName;

	@JsonProperty("cost")
	private CostRequest cost;

	@JsonProperty("is_pre_order_item")
	private Boolean isPreOrderItem;

	@JsonProperty("introduction")
	private String introduction;

	@JsonProperty("pre_order_time")
	private PreOrderScheduleRequest preOrderTime;

	@JsonProperty("created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime createdAt;

	@JsonProperty("seller_id")
	private String sellerId;

	@JsonProperty("seller_name")
	private String sellerName;

	@Builder
	public ItemDetailResponse(Long itemId, String itemName, CostRequest cost, Boolean isPreOrderItem,
		String introduction, PreOrderScheduleRequest preOrderTime, LocalDateTime createdAt, String sellerId,
		String sellerName) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.cost = cost;
		this.introduction = introduction;
		this.isPreOrderItem = isPreOrderItem;
		this.preOrderTime = preOrderTime;
		this.createdAt = createdAt;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
	}

	public static ItemDetailResponse of(ItemDetailDto itemDetailDto) {
		return ItemDetailResponse.builder()
			.itemId(itemDetailDto.getItemId())
			.itemName(itemDetailDto.getItemName())
			.cost(CostRequest.builder()
				.price(itemDetailDto.getPrice())
				.discount(itemDetailDto.getDiscount())
				.build()
			)
			.introduction(itemDetailDto.getIntroduction())
			.isPreOrderItem(itemDetailDto.getIsPreOrderItem())
			.preOrderTime(
				PreOrderScheduleRequest.fromLocalDateTime(itemDetailDto.getPreOrderTime()))
			.createdAt(itemDetailDto.getCreatedAt())
			.sellerId(itemDetailDto.getSellerId())
			.sellerName(itemDetailDto.getSellerName())
			.build();
	}
}
