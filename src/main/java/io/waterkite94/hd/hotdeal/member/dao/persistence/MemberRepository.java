package io.waterkite94.hd.hotdeal.member.dao.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.member.dao.persistence.custom.MemberRepositoryCustom;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {

	Optional<MemberEntity> findByMemberId(String email);

	List<MemberEntity> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
