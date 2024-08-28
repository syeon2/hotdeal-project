package io.waterkite94.hd.hotdeal.order.web.api.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemsRequest {

	@JsonProperty("order_items")
	private List<OrderItemRequest> orderItems;

	@JsonProperty("address")
	private AddressRequest address;

	@JsonProperty("member_id")
	private String memberId;

	@Builder
	private OrderItemsRequest(List<OrderItemRequest> orderItems, AddressRequest address, String memberId) {
		this.orderItems = orderItems;
		this.address = address;
		this.memberId = memberId;
	}
}
