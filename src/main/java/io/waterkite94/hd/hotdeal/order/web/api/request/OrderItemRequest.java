package io.waterkite94.hd.hotdeal.order.web.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.order.domain.dto.AddOrderItemDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemRequest {

	@JsonProperty("item_id")
	@NotNull(message = "상품 아이디는 필수 값입니다.")
	private Long itemId;

	@JsonProperty("quantity")
	@NotNull(message = "상품 주문 개수는 필수 값입니다.")
	@Min(value = 1, message = "주문 개수는 0 이하가 될 수 없습니다.")
	private Integer quantity;

	@Builder
	private OrderItemRequest(Long itemId, Integer quantity) {
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public AddOrderItemDto toServiceDto() {
		return AddOrderItemDto.of(itemId, quantity);
	}
}
