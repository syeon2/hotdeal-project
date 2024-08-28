package io.waterkite94.hd.hotdeal.order.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemsResponse {

	@JsonProperty("order_uuid")
	private String orderUuid;

	public OrderItemsResponse(String orderUuid) {
		this.orderUuid = orderUuid;
	}
}
