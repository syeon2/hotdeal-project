package io.waterkite94.hd.hotdeal.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.order.dao.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
