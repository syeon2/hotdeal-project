package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.item.dao.custom.ItemRepositoryCustom;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>, ItemRepositoryCustom {

	@Modifying(clearAutomatically = true)
	@Transactional
	void deleteByUuid(String uuid);
}
