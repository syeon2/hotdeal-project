package io.waterkite94.hd.hotdeal.member.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.AddressEntity;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.MemberEntity;
import io.waterkite94.hd.hotdeal.member.domain.Address;
import io.waterkite94.hd.hotdeal.member.domain.Member;
import jakarta.transaction.Transactional;

class MemberProfileServiceTest extends IntegrationTestSupport {

	@Autowired
	private MemberProfileService memberProfileService;

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
	void createMember() {
		// given
		Member member = createMemberDomain("waterkite94@gmail.com", "00011112222", "password");
		Address address = createAddressDomain();

		// when
		String memberId = memberProfileService.createMember(member, address);

		// then
		MemberEntity findMember = memberRepository.findByMemberId(memberId).orElse(null);
		AddressEntity findAddress = addressRepository.findByMemberId(memberId).orElse(null);

		assertThat(findMember).isNotNull();
		assertThat(findAddress).isNotNull();
		assertThat(findAddress.getMemberId()).isEqualTo(findMember.getMemberId());
	}

	@Test
	@DisplayName(value = "회원 가입 시 중복된 이메일이 존재하면 예외를 반환합니다.")
	@Transactional
	void createMember_duplicatedEmail() {
		// given
		Member member = createMemberDomain("waterkite94@gmail.com", "00011112222", "password");
		Address address = createAddressDomain();

		memberProfileService.createMember(member, address);

		// when  // then
		assertThatThrownBy(() -> memberProfileService.createMember(member, address))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Email Or Phone Number is already use");
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 가입 시 입력한 비밀번호는 암호화됩니다.")
	void createMember_encryptPassword() {
		// given
		Member member = createMemberDomain("waterkite94@gmail.com", "00011112222", "password");
		Address address = createAddressDomain();

		String savedMemberId = memberProfileService.createMember(member, address);

		// when
		MemberEntity findMember = memberRepository.findByMemberId(savedMemberId).orElse(null);

		// then
		assertThat(findMember).isNotNull();
		assertThat(findMember.getPassword()).isNotEqualTo(member.getPassword());
	}

	private Address createAddressDomain() {
		return Address.builder()
			.city("city")
			.state("state")
			.address("address")
			.zipcode("12345")
			.build();
	}

	private Member createMemberDomain(String email, String phoneNumber, String password) {
		return Member.builder()
			.email(email)
			.password(password)
			.name("suyeon")
			.phoneNumber(phoneNumber)
			.build();
	}
}
