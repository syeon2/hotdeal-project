package io.waterkite94.hd.hotdeal.item.web.api.request;

import java.time.LocalDateTime;

import io.waterkite94.hd.hotdeal.item.domain.Item;
import io.waterkite94.hd.hotdeal.item.domain.ItemType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemRequest {

	@NotBlank(message = "이름은 빈칸을 허용하지 않습니다.")
	private String name;

	@NotNull(message = "가격은 필수 값입니다.")
	@Min(value = 0, message = "가격은 0이하가 될 수 없습니다.")
	private Integer price;

	@NotNull(message = "할인 금액은 필수 값입니다.")
	@Min(value = 0, message = "할인 금액은 0이하가 될 수 없습니다.")
	private Integer discount;

	@NotBlank(message = "상품 설명은 빈칸을 허용하지 않습니다.")
	private String introduction;

	private ItemType type;

	private Integer year;

	private Integer month;

	private Integer date;

	private Integer hour;

	private Integer minute;

	@NotNull(message = "카테고리 아이디는 빈칸을 허용하지 않습니다.")
	private Long categoryId;

	@Builder
	private AddItemRequest(String name, Integer price, Integer discount, String introduction, ItemType type,
		Integer year,
		Integer month, Integer date, Integer hour, Integer minute, Long categoryId) {
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.introduction = introduction;
		this.type = type;
		this.year = year;
		this.month = month;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
		this.categoryId = categoryId;
	}

	public Item toDomain() {
		return Item.builder()
			.name(name)
			.price(price)
			.discount(discount)
			.introduction(introduction)
			.type(type)
			.preOrderTime(LocalDateTime.of(year, month, date, hour, minute))
			.categoryId(categoryId)
			.build();
	}
}
