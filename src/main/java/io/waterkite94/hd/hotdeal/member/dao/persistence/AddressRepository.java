package io.waterkite94.hd.hotdeal.member.dao.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.member.dao.persistence.custom.AddressRepositoryCustom;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long>, AddressRepositoryCustom {

	Optional<AddressEntity> findByMemberId(String address);
}
