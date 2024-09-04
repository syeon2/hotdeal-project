package io.waterkite94.hd.hotdeal.item.dao.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.item.dao.persistence.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	Optional<CategoryEntity> findByName(String name);
}
