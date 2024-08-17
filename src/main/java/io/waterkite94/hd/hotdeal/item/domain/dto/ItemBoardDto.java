package io.waterkite94.hd.hotdeal.item.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemBoardDto {

	private Long id;

	private String name;

	private Integer price;

	private Integer discount;

	private String introduction;

	private boolean isPreOrderItem;

	private LocalDateTime preOrderTime;

	private String sellerName;

	private String sellerId;

	@Builder
	public ItemBoardDto(Long id, String name, Integer price, Integer discount, String introduction,
		boolean isPreOrderItem, LocalDateTime preOrderTime, String sellerName, String sellerId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.introduction = introduction;
		this.isPreOrderItem = isPreOrderItem;
		this.preOrderTime = preOrderTime;
		this.sellerName = sellerName;
		this.sellerId = sellerId;
	}
}
