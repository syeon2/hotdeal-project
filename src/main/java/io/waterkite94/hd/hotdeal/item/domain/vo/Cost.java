package io.waterkite94.hd.hotdeal.item.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Cost {

	private final Integer price;
	private final Integer discount;

	@Builder
	private Cost(Integer price, Integer discount) {
		this.price = price;
		this.discount = discount;
	}

	public static Cost of(Integer price, Integer discount) {
		return new Cost(price, discount);
	}

	public boolean isCorrectCost() {
		return discount <= price;
	}
}
