package io.waterkite94.hd.hotdeal.item.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import io.waterkite94.hd.hotdeal.item.dao.custom.ItemRepositoryCustom;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemEntity;
import jakarta.persistence.LockModeType;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>, ItemRepositoryCustom {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select i from ItemEntity i where i.id = :id")
	Optional<ItemEntity> findItemEntityForQuantityUpdate(Long id);
}
