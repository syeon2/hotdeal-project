package io.waterkite94.hd.hotdeal.member.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.member.dao.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	Optional<MemberEntity> findByMemberId(String email);

	List<MemberEntity> findByEmailAndPhoneNumber(String email, String phoneNumber);
}
