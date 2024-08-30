package io.waterkite94.hd.hotdeal.item.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Cost {

	private final Integer price;
	private final Integer discount;

	@Builder
	private Cost(Integer price, Integer discount) {
		validateCost(price, discount);

		this.price = price;
		this.discount = discount;
	}

	public static Cost of(Integer price, Integer discount) {
		return new Cost(price, discount);
	}

	private void validateCost(Integer price, Integer discount) {
		if (discount > price || price < 0 || discount < 0) {
			throw new IllegalArgumentException("Invalid cost");
		}
	}
}
