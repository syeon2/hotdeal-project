package io.waterkite94.hd.hotdeal.order.dao.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import io.waterkite94.hd.hotdeal.common.wrapper.BaseEntity;
import io.waterkite94.hd.hotdeal.order.domain.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	@Column(name = "uuid", columnDefinition = "varchar(60)", nullable = false, unique = true)
	private String uuid;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", columnDefinition = "varchar(30)", nullable = false)
	private OrderStatus status;

	@Column(name = "address", columnDefinition = "varchar(255)", nullable = false)
	private String address;

	@Column(name = "member_id", columnDefinition = "varchar(60)", nullable = false)
	private String memberId;

	@OneToMany(
		mappedBy = "order",
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.LAZY)
	private List<OrderDetailEntity> orderDetail = new ArrayList<>();

	@Builder
	private OrderEntity(Long id, String uuid, OrderStatus status, String address, String memberId) {
		this.id = id;
		this.uuid = uuid;
		this.status = status;
		this.address = address;
		this.memberId = memberId;
	}

	public void addOrderDetail(OrderDetailEntity orderDetail) {
		this.orderDetail.add(orderDetail);
		orderDetail.addOrder(this);
	}

	public void changeStatus(OrderStatus status) {
		this.status = status;
	}
}
