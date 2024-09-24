package io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.custom;

import java.util.Optional;

import io.waterkite94.hd.hotdeal.member.api.domain.dto.AccessMemberDto;

public interface MemberRepositoryCustom {

	Optional<AccessMemberDto> findMemberWithAddress(String memberId);
}
