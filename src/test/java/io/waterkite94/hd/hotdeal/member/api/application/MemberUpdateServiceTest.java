package io.waterkite94.hd.hotdeal.member.api.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.member.api.domain.Address;
import io.waterkite94.hd.hotdeal.member.api.domain.MemberRole;
import io.waterkite94.hd.hotdeal.member.api.domain.dto.UpdateMemberDto;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.entity.AddressEntity;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.entity.MemberEntity;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.redis.VerificationCodeRedisAdapter;
import jakarta.transaction.Transactional;

class MemberUpdateServiceTest extends IntegrationTestSupport {

	@Autowired
	private MemberUpdateService memberUpdateService;

	@MockBean
	private VerificationCodeRedisAdapter verificationCodeRedisAdapter;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AddressRepository addressRepository;

	@BeforeEach
	void before() {
		memberRepository.deleteAllInBatch();
		addressRepository.deleteAllInBatch();
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 정보를 변경합니다.")
	void updateMemberInfo() {
		// given
		String memberId = "memberId";
		MemberEntity memberEntity = createMemberEntity(memberId);
		AddressEntity addressEntity = updateAddressEntity("city", "state", "address", "111111", memberId);

		memberRepository.save(memberEntity);
		addressRepository.save(addressEntity);

		// when
		UpdateMemberDto updateMemberDto = createUpdateMemberDto();
		Address updateAddressDto = createUpdateAddressDto();

		memberUpdateService.updateMemberInfo(memberId, updateMemberDto, updateAddressDto);

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findByMemberId(memberId);
		assertThat(findMemberOptional).isPresent()
			.get()
			.extracting("name", "phoneNumber")
			.contains(updateMemberDto.getName(), updateMemberDto.getPhoneNumber());

		Optional<AddressEntity> findAddressOptional = addressRepository.findByMemberId(memberId);
		assertThat(findAddressOptional).isPresent()
			.get()
			.extracting("city", "state", "address", "zipcode")
			.containsExactlyInAnyOrder(updateAddressDto.getCity(), updateAddressDto.getState(),
				updateAddressDto.getAddress(), updateAddressDto.getZipcode());
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 이메일을 변경합니다.")
	void updateMemberEmail() {
		// given
		String memberId = "memberId";
		String verificationCode = "01234566789";

		MemberEntity memberEntity = createMemberEntity(memberId);

		memberRepository.save(memberEntity);

		given(verificationCodeRedisAdapter.getVerificationCode(anyString()))
			.willReturn(Optional.of(verificationCode));

		// when
		String changedEmail = "changed@example.com";

		memberUpdateService.updateMemberEmail(memberId, changedEmail, verificationCode);

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findByMemberId(memberId);
		assertThat(findMemberOptional).isPresent();
		assertThat(findMemberOptional.get().getEmail()).isEqualTo(changedEmail);
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 비밀번호를 변경합니다.")
	void updateMemberPassword() {
		// given
		String memberId = "memberId";
		String encryptedPassword = "encryptedPassword";
		MemberEntity memberEntity = createMemberEntity(memberId);

		memberRepository.save(memberEntity);

		given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
		given(passwordEncoder.encode(anyString())).willReturn(encryptedPassword);

		// when
		String currentPassword = "currentPassword";
		String newPassword = "newPassword";

		memberUpdateService.updateMemberPassword(memberId, currentPassword, newPassword);

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findByMemberId(memberId);
		assertThat(findMemberOptional).isPresent();
		assertThat(findMemberOptional.get().getPassword()).isEqualTo(encryptedPassword);
	}

	private static Address createUpdateAddressDto() {
		return Address.builder()
			.city("newCity")
			.state("newState")
			.address("newAddress")
			.zipcode("222222")
			.build();
	}

	private static UpdateMemberDto createUpdateMemberDto() {
		return UpdateMemberDto.builder()
			.name("changeName")
			.phoneNumber("11122223333")
			.build();
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

	private AddressEntity updateAddressEntity(String city, String state, String address, String zipcode,
		String memberId) {
		return AddressEntity.builder()
			.city(city)
			.state(state)
			.address(address)
			.zipcode(zipcode)
			.memberId(memberId)
			.build();
	}
}
