package io.waterkite94.hd.hotdeal.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.domain.dto.AccessMemberDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAccessService {

	private final MemberRepository memberRepository;

	@Transactional
	public AccessMemberDto accessMember(String memberId) {
		return findMemberDtoWithAddress(memberId);
	}

	private AccessMemberDto findMemberDtoWithAddress(String memberId) {
		Optional<AccessMemberDto> findMemberOptional = memberRepository.findMemberWithAddress(memberId);
		if (findMemberOptional.isEmpty()) {
			throw new IllegalArgumentException("Member not found");
		}
		return findMemberOptional.get();
	}

}
