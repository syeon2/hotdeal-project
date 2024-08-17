package io.waterkite94.hd.hotdeal.item.web.api.request;

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

	@NotBlank(message = "회원 아이디는 빈칸을 허용하지 않습니다.")
	private String memberId;

	@NotNull(message = "카테고리 아이디는 빈칸을 허용하지 않습니다.")
	private Long categoryId;

	@Builder
	public AddItemRequest(String name, Integer price, Integer discount, String introduction, ItemType type,
		String memberId, Long categoryId) {
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.introduction = introduction;
		this.type = type;
		this.memberId = memberId;
		this.categoryId = categoryId;
	}

	public Item toDomain() {
		return Item.builder()
			.name(name)
			.price(price)
			.discount(discount)
			.introduction(introduction)
			.type(type)
			.memberId(memberId)
			.categoryId(categoryId)
			.build();
	}
}
