package io.waterkite94.hd.hotdeal.order.dao.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.order.dao.persistence.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

	Optional<OrderEntity> findByUuid(String orderUuid);
}
