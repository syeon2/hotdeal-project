package io.waterkite94.hd.hotdeal.member.api.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.member.api.domain.dto.AccessMemberDto;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.MemberRepository;
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
