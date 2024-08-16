package io.waterkite94.hd.hotdeal.member.dao.persistence.custom;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.MemberEntity;
import io.waterkite94.hd.hotdeal.member.domain.dto.UpdateMemberDto;

class MemberRepositoryImplTest extends IntegrationTestSupport {

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void before() {
		memberRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName(value = "회원 정보를 업데이트합니다.")
	void updateMemberInfo() {
		// given
		String memberId = "memberId";
		MemberEntity memberEntity = createMemberEntity(memberId, "test@example.com", "suyeon", "00011112222");
		memberRepository.save(memberEntity);

		// when
		String changedEmail = "changeEmail@example.com";
		String changedName = "changeName";
		String changedPhoneNumber = "111122223333";
		UpdateMemberDto updateMemberDto = createUpdateMemberDto(changedEmail, changedName, changedPhoneNumber);
		memberRepository.updateMemberInfo(memberId, updateMemberDto);

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findByMemberId(memberId);
		assertThat(findMemberOptional).isPresent()
			.get()
			.extracting("email", "name", "phoneNumber")
			.containsExactlyInAnyOrder(changedEmail, changedName, changedPhoneNumber);
	}

	private UpdateMemberDto createUpdateMemberDto(String email, String name, String phoneNumber) {
		return UpdateMemberDto.builder()
			.email(email)
			.name(name)
			.phoneNumber(phoneNumber)
			.build();
	}

	private MemberEntity createMemberEntity(String memberId, String mail, String name, String phoneNumber) {
		return MemberEntity.builder()
			.memberId(memberId)
			.email(mail)
			.password("12345678")
			.name(name)
			.phoneNumber(phoneNumber)
			.build();
	}
}
