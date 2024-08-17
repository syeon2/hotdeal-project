package io.waterkite94.hd.hotdeal.member.dao.persistence.custom;

import java.util.Optional;

import io.waterkite94.hd.hotdeal.member.domain.dto.AccessMemberDto;

public interface MemberRepositoryCustom {

	Optional<AccessMemberDto> findMemberWithAddress(String memberId);
}
