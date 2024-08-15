package io.waterkite94.hd.hotdeal.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.member.dao.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	List<MemberEntity> findByEmailAndPhoneNumber(String email, String phoneNumber);
}
