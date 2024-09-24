package io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.custom;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.entity.AddressEntity;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.entity.MemberEntity;
import io.waterkite94.hd.hotdeal.member.api.domain.MemberRole;
import io.waterkite94.hd.hotdeal.member.api.domain.dto.AccessMemberDto;

class MemberRepositoryImplTest extends IntegrationTestSupport {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AddressRepository addressRepository;

	@BeforeEach
	void before() {
		memberRepository.deleteAllInBatch();
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 정보와 주소 데이터를 Join으로 가져옵니다.")
	void findMemberWithAddress() {
		// given
		String memberId = "memberId";
		MemberEntity memberEntity = createMemberEntity(memberId);
		AddressEntity addressEntity = createAddressEntity(memberId);

		memberRepository.save(memberEntity);
		addressRepository.save(addressEntity);

		// when
		Optional<AccessMemberDto> findMemberWithAddressDtoOptional = memberRepository.findMemberWithAddress(memberId);

		// then
		assertThat(findMemberWithAddressDtoOptional).isPresent();
		assertThat(findMemberWithAddressDtoOptional.get().getMemberId()).isEqualTo(memberId);
	}

	private MemberEntity createMemberEntity(String memberId) {
		return MemberEntity.builder()
			.memberId(memberId)
			.email("test@example.com")
			.password("password")
			.name("name")
			.phoneNumber("00011112222")
			.role(MemberRole.USER_NORMAL)
			.build();
	}

	private AddressEntity createAddressEntity(String memberId) {
		return AddressEntity.builder()
			.city("city")
			.state("state")
			.address("address")
			.zipcode("zipcode")
			.memberId(memberId)
			.build();
	}
}
