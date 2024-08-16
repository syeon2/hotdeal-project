package io.waterkite94.hd.hotdeal.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.common.error.exception.DuplicatedAccountException;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.AddressEntity;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.MemberEntity;
import io.waterkite94.hd.hotdeal.member.dao.redis.VerificationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.domain.Address;
import io.waterkite94.hd.hotdeal.member.domain.Member;
import io.waterkite94.hd.hotdeal.member.domain.MemberRole;
import jakarta.transaction.Transactional;

class MemberJoinServiceTest extends IntegrationTestSupport {

	@Autowired
	private MemberJoinService memberJoinService;

	@MockBean
	private VerificationCodeRedisAdapter verificationCodeRedisAdapter;

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
	@DisplayName(value = "회원정보를 통해 회원을 저장합니다.")
	void joinMember() {
		// given
		String email = "waterkite94@gmail.com";
		String verificationCode = "0123456789";

		Member member = createMemberDomain(email, "00011112222");
		Address address = createAddressDomain();

		// when
		given(verificationCodeRedisAdapter.getVerificationCode(email))
			.willReturn(Optional.of(verificationCode));

		String memberId = memberJoinService.joinMember(member, address, verificationCode);

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findByMemberId(memberId);
		Optional<AddressEntity> findAddressOptional = addressRepository.findByMemberId(memberId);

		assertThat(findMemberOptional).isPresent();
		assertThat(findAddressOptional).isPresent();
		assertThat(findMemberOptional.get().getMemberId())
			.isEqualTo(findAddressOptional.get().getMemberId());
	}

	@Test
	@DisplayName(value = "회원 가입 시 중복된 이메일이 존재하면 예외를 반환합니다.")
	@Transactional
	void joinMember_duplicatedEmail() {
		// given
		String email = "waterkite94@gmail.com";
		String verificationCode = "0123456789";

		Member member = createMemberDomain(email, "00011112222");
		Address address = createAddressDomain();

		given(verificationCodeRedisAdapter.getVerificationCode(anyString()))
			.willReturn(Optional.of(verificationCode));

		memberJoinService.joinMember(member, address, verificationCode);

		// when  // then
		Member newMember = createMemberDomain(email, "11122223333");

		assertThatThrownBy(() -> memberJoinService.joinMember(newMember, address, verificationCode))
			.isInstanceOf(DuplicatedAccountException.class)
			.hasMessage("Email Or Phone Number is already use");
	}

	@Test
	@DisplayName(value = "회원 가입 시 중복된 전화번호가 존재하면 예외를 반환합니다.")
	@Transactional
	void joinMember_duplicatedPhoneNumber() {
		// given
		String phoneNumber = "00011112222";
		String authenticationCode = "0123456789";

		Member member = createMemberDomain("waterkite94@gmail.com", phoneNumber);
		Address address = createAddressDomain();

		given(verificationCodeRedisAdapter.getVerificationCode(anyString()))
			.willReturn(Optional.of(authenticationCode));

		memberJoinService.joinMember(member, address, authenticationCode);

		// when  // then
		Member newMember = createMemberDomain("another@example.com", phoneNumber);

		assertThatThrownBy(() -> memberJoinService.joinMember(newMember, address, authenticationCode))
			.isInstanceOf(DuplicatedAccountException.class)
			.hasMessage("Email Or Phone Number is already use");
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 가입 시 입력한 비밀번호는 암호화됩니다.")
	void joinMember_encryptPassword() {
		// given
		String authenticationCode = "0123456789";

		Member member = createMemberDomain("waterkite94@gmail.com", "00011112222");
		Address address = createAddressDomain();

		given(verificationCodeRedisAdapter.getVerificationCode(anyString()))
			.willReturn(Optional.of(authenticationCode));

		String savedMemberId = memberJoinService.joinMember(member, address, authenticationCode);

		// when
		Optional<MemberEntity> findMemberOptional = memberRepository.findByMemberId(savedMemberId);

		// then
		assertThat(findMemberOptional).isPresent();
		assertThat(findMemberOptional.get().getPassword()).isNotEqualTo(member.getPassword());
		assertThat(findMemberOptional.get().getPassword().length()).isEqualTo(60);
	}

	private Address createAddressDomain() {
		return Address.builder()
			.city("city")
			.state("state")
			.address("address")
			.zipcode("12345")
			.build();
	}

	private Member createMemberDomain(String email, String phoneNumber) {
		return Member.builder()
			.email(email)
			.password("password")
			.name("name")
			.phoneNumber(phoneNumber)
			.role(MemberRole.USER_NORMAL)
			.build();
	}
}
