package io.waterkite94.hd.hotdeal.order.api.infrastructure.persistence.entity;

import io.waterkite94.hd.hotdeal.support.wrapper.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	@Column(name = "total_price", columnDefinition = "int", nullable = false)
	private int totalPrice;

	@Column(name = "total_discount", columnDefinition = "int", nullable = false)
	private Integer totalDiscount;

	@Column(name = "quantity", columnDefinition = "int", nullable = false)
	private Integer quantity;

	@Column(name = "item_id", columnDefinition = "bigint", nullable = false)
	private Long itemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "uuid")
	private OrderEntity order;

	@Builder
	private OrderDetailEntity(Long id, int totalPrice, Integer totalDiscount, Integer quantity, Long itemId,
		OrderEntity order) {
		this.id = id;
		this.totalPrice = totalPrice;
		this.totalDiscount = totalDiscount;
		this.quantity = quantity;
		this.itemId = itemId;
		this.order = order;
	}

	public void addOrder(OrderEntity order) {
		this.order = order;
	}
}
