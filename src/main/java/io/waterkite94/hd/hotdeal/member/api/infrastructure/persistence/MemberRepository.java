package io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.custom.MemberRepositoryCustom;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {

	Optional<MemberEntity> findByMemberId(String email);

	List<MemberEntity> findByEmailOrPhoneNumber(String email, String phoneNumber);

	List<MemberEntity> findByPhoneNumber(String phoneNumber);

	List<MemberEntity> findByEmail(String email);
}
