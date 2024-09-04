package io.waterkite94.hd.hotdeal.item.dao.persistence.entity;

import io.waterkite94.hd.hotdeal.common.wrapper.BaseEntity;
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
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	@Column(name = "name", columnDefinition = "varchar(60)", nullable = false, unique = true)
	private String name;

	@Builder
	private CategoryEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
