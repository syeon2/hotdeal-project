package io.waterkite94.hd.hotdeal.member.service;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.domain.MemberRole;
import io.waterkite94.hd.hotdeal.member.domain.dto.AccessMemberDto;

class MemberAccessServiceTest extends IntegrationTestSupport {

	@Autowired
	private MemberAccessService memberAccessService;

	@MockBean
	private MemberRepository memberRepository;

	@Test
	@Transactional
	@DisplayName(value = "회원과 주소를 Join한 데이터를 조회합니다.")
	void accessMember() {
		// given
		String memberId = "memberId";

		AccessMemberDto accessMemberDto = createAccessMemberDto(memberId);
		given(memberRepository.findMemberWithAddress(memberId))
			.willReturn(Optional.of(accessMemberDto));

		// when
		memberAccessService.accessMember(memberId);

		// then
		verify(memberRepository, times(1)).findMemberWithAddress(memberId);
	}

	private static AccessMemberDto createAccessMemberDto(String memberId) {
		return AccessMemberDto.builder()
			.memberId(memberId)
			.email("test@example.com")
			.name("name")
			.phoneNumber("00011122222")
			.role(MemberRole.USER_NORMAL)
			.city("city")
			.state("state")
			.address("address")
			.zipcode("123456")
			.build();
	}
}
