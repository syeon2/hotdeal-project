package io.waterkite94.hd.hotdeal.member.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.DuplicatedAccountException;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressMapper;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberMapper;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.dao.redis.AuthenticationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.domain.Address;
import io.waterkite94.hd.hotdeal.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberProfileService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	private final AddressRepository addressRepository;
	private final AddressMapper addressMapper;

	private final AuthenticationCodeRedisAdapter authenticationCodeRedisAdapter;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public String createMember(Member member, Address address, String authenticationCode) {
		validateEmailAuthenticationCode(member.getEmail(), authenticationCode);
		validateEmailAndPhoneNumber(member.getEmail(), member.getPhoneNumber());

		Member initedMember = member.initializeMember(
			createUuid(),
			encryptedPassword(member.getPassword())
		);
		memberRepository.save(memberMapper.toEntity(initedMember));

		Address initedAddress = address.initialize(initedMember.getMemberId());
		addressRepository.save(addressMapper.toEntity(initedAddress));

		return initedMember.getMemberId();
	}

	private void validateEmailAuthenticationCode(String email, String authenticationCode) {
		String findCode = authenticationCodeRedisAdapter.getAuthenticationCode(email);

		if (!findCode.equals(authenticationCode)) {
			throw new IllegalArgumentException("Invalid email authentication code");
		}
	}

	private void validateEmailAndPhoneNumber(String email, String phoneNumber) {
		if (!memberRepository.findByEmailOrPhoneNumber(email, phoneNumber).isEmpty()) {
			throw new DuplicatedAccountException("Email Or Phone Number is already use");
		}
	}

	private String createUuid() {
		return UUID.randomUUID().toString();
	}

	private String encryptedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
