package io.waterkite94.hd.hotdeal.member.dao.persistence.custom;

import io.waterkite94.hd.hotdeal.member.domain.dto.UpdateMemberDto;

public interface MemberRepositoryCustom {

	void updateMemberInfo(String memberId, UpdateMemberDto updateMemberDto);
}
