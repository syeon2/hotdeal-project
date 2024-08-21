package io.waterkite94.hd.hotdeal.item.web.api.response.vo;

import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CostResponse {

	private final Integer price;
	private final Integer discount;

	@Builder
	private CostResponse(Integer price, Integer discount) {
		this.price = price;
		this.discount = discount;
	}

	public static CostResponse of(Cost cost) {
		return new CostResponse(cost.getPrice(), cost.getDiscount());
	}
}
