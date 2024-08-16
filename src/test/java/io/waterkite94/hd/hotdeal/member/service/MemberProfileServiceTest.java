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
import io.waterkite94.hd.hotdeal.member.dao.redis.AuthenticationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.domain.Address;
import io.waterkite94.hd.hotdeal.member.domain.Member;
import io.waterkite94.hd.hotdeal.member.domain.dto.UpdateMemberDto;
import jakarta.transaction.Transactional;

class MemberProfileServiceTest extends IntegrationTestSupport {

	@Autowired
	private MemberProfileService memberProfileService;

	@MockBean
	private AuthenticationCodeRedisAdapter authenticationCodeRedisAdapter;

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
		String email = "waterkite94@gmail.com";
		String authenticationCode = "123456";

		Member member = createMemberDomain(email, "00011112222", "password");
		Address address = createAddressDomain();

		// when
		given(authenticationCodeRedisAdapter.getAuthenticationCode(email))
			.willReturn(authenticationCode);

		String memberId = memberProfileService.createMember(member, address, authenticationCode);

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
		String email = "waterkite94@gmail.com";
		String phoneNumber = "00011112222";

		Member member = createMemberDomain(email, phoneNumber, "password");
		Address address = createAddressDomain();
		String authenticationCode = "123456";

		given(authenticationCodeRedisAdapter.getAuthenticationCode(anyString()))
			.willReturn(authenticationCode);

		memberProfileService.createMember(member, address, authenticationCode);

		Member newMember = createMemberDomain(email, "11122223333", "password");

		// when  // then
		assertThatThrownBy(() -> memberProfileService.createMember(newMember, address, authenticationCode))
			.isInstanceOf(DuplicatedAccountException.class)
			.hasMessage("Email Or Phone Number is already use");
	}

	@Test
	@DisplayName(value = "회원 가입 시 중복된 전화번호가 존재하면 예외를 반환합니다.")
	@Transactional
	void createMember_duplicatedPhoneNumber() {
		// given
		String email = "waterkite94@gmail.com";
		String phoneNumber = "00011112222";

		Member member = createMemberDomain(email, phoneNumber, "password");
		Address address = createAddressDomain();
		String authenticationCode = "123456";

		given(authenticationCodeRedisAdapter.getAuthenticationCode(anyString()))
			.willReturn(authenticationCode);

		memberProfileService.createMember(member, address, authenticationCode);

		Member newMember = createMemberDomain("another@example.com", phoneNumber, "password");

		// when  // then
		assertThatThrownBy(() -> memberProfileService.createMember(newMember, address, authenticationCode))
			.isInstanceOf(DuplicatedAccountException.class)
			.hasMessage("Email Or Phone Number is already use");
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 가입 시 입력한 비밀번호는 암호화됩니다.")
	void createMember_encryptPassword() {
		// given
		String email = "waterkite94@gmail.com";
		String authenticationCode = "123456";
		Member member = createMemberDomain(email, "00011112222", "password");
		Address address = createAddressDomain();

		given(authenticationCodeRedisAdapter.getAuthenticationCode(email))
			.willReturn(authenticationCode);

		String savedMemberId = memberProfileService.createMember(member, address, authenticationCode);

		// when
		MemberEntity findMember = memberRepository.findByMemberId(savedMemberId).orElse(null);

		// then
		assertThat(findMember).isNotNull();
		assertThat(findMember.getPassword()).isNotEqualTo(member.getPassword());
	}

	@Test
	@Transactional
	@DisplayName(value = "회원 정보를 변경합니다.")
	void updateMember() {
		// given
		String memberId = "memberId";
		MemberEntity memberEntity = updateMemberEntity("test@example.com", "00011112222", "name", memberId);
		AddressEntity addressEntity = updateAddressEntity("city", "state", "address", "111111", memberId);

		memberRepository.save(memberEntity);
		addressRepository.save(addressEntity);

		// when
		UpdateMemberDto updateMemberDto = UpdateMemberDto.builder()
			.email("change@example.com")
			.name("changeName")
			.phoneNumber("11122223333")
			.build();

		Address updateAddressDto = Address.builder()
			.city("newCity")
			.state("newState")
			.address("newAddress")
			.zipcode("222222")
			.build();

		memberProfileService.updateMemberInfo(memberId, updateMemberDto, updateAddressDto);

		// then
		Optional<MemberEntity> findMemberOptional = memberRepository.findByMemberId(memberId);
		assertThat(findMemberOptional).isPresent()
			.get()
			.extracting("email", "name", "phoneNumber")
			.contains(updateMemberDto.getEmail(), updateMemberDto.getName(), updateMemberDto.getPhoneNumber());

		Optional<AddressEntity> findAddressOptional = addressRepository.findByMemberId(memberId);
		assertThat(findAddressOptional).isPresent()
			.get()
			.extracting("city", "state", "address", "zipcode")
			.containsExactlyInAnyOrder(updateAddressDto.getCity(), updateAddressDto.getState(),
				updateAddressDto.getAddress(), updateAddressDto.getZipcode());
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

	private MemberEntity updateMemberEntity(String email, String phoneNumber, String name, String memberId) {
		return MemberEntity.builder()
			.memberId(memberId)
			.email(email)
			.password("password")
			.name(name)
			.phoneNumber(phoneNumber)
			.build();
	}
}
