package io.waterkite94.hd.hotdeal.member.dao.persistence;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.MemberEntity;
import io.waterkite94.hd.hotdeal.member.domain.Member;

@Component
public class MemberMapper {

	public MemberEntity toEntity(Member member) {
		return MemberEntity.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.password(member.getPassword())
			.name(member.getName())
			.phoneNumber(member.getPhoneNumber())
			.role(member.getRole())
			.build();
	}

	public Member toDomain(MemberEntity member) {
		return Member.builder()
			.id(member.getId())
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.name(member.getName())
			.phoneNumber(member.getPhoneNumber())
			.role(member.getRole())
			.build();
	}
}
