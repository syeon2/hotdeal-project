package io.waterkite94.hd.hotdeal.order.api.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PreOrderItemRequest {

	@JsonProperty("order_items")
	private PreOrderItemsRequest orderItems;

	@JsonProperty("address")
	private AddressRequest address;

	@JsonProperty("member_id")
	private String memberId;

	@Builder
	private PreOrderItemRequest(PreOrderItemsRequest orderItems, AddressRequest address, String memberId) {
		this.orderItems = orderItems;
		this.address = address;
		this.memberId = memberId;
	}
}
