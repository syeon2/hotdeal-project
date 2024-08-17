package io.waterkite94.hd.hotdeal.item.dao.entity;

import io.waterkite94.hd.hotdeal.common.wrapper.BaseEntity;
import io.waterkite94.hd.hotdeal.item.domain.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	@Column(name = "item_id", columnDefinition = "varchar(60)", nullable = false)
	private Long itemId;

	@Column(name = "name", columnDefinition = "varchar(60)", nullable = false)
	private String name;

	@Column(name = "price", columnDefinition = "int", nullable = false)
	private Integer price;

	@Column(name = "discount", columnDefinition = "int", nullable = false)
	private Integer discount;

	@Column(name = "introduction", columnDefinition = "varchar(255)", nullable = false)
	private String introduction;

	@Column(name = "type", columnDefinition = "varchar(60)", nullable = false)
	private ItemType type;

	@Column(name = "category_id", columnDefinition = "bigint", nullable = false)
	private Long categoryId;

	@Column(name = "member_id", columnDefinition = "varchar(60)", nullable = false)
	private String memberId;

	@Builder
	private ItemEntity(Long id, Long itemId, String name, Integer price, Integer discount, String introduction,
		ItemType type, Long categoryId, String memberId) {
		this.id = id;
		this.itemId = itemId;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.introduction = introduction;
		this.type = type;
		this.categoryId = categoryId;
		this.memberId = memberId;
	}
}
