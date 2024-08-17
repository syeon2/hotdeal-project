package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
