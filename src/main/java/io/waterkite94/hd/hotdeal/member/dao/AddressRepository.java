package io.waterkite94.hd.hotdeal.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.member.dao.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	Optional<AddressEntity> findByMemberId(String address);
}
