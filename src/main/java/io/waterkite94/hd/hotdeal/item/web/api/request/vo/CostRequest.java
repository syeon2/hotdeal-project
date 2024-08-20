package io.waterkite94.hd.hotdeal.item.web.api.request.vo;

import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CostRequest {

	@NotNull(message = "가격은 필수 값입니다.")
	@Min(value = 0, message = "가격은 0이하가 될 수 없습니다.")
	private Integer price;

	@NotNull(message = "할인 금액은 필수 값입니다.")
	@Min(value = 0, message = "할인 금액은 0이하가 될 수 없습니다.")
	private Integer discount;

	@Builder
	private CostRequest(Integer price, Integer discount) {
		this.price = price;
		this.discount = discount;
	}

	public Cost toVo() {
		return Cost.of(this.price, this.discount);
	}
}
