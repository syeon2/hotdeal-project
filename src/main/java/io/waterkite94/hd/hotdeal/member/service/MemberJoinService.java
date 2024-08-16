package io.waterkite94.hd.hotdeal.member.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.DuplicatedAccountException;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressMapper;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberMapper;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.dao.redis.VerificationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.domain.Address;
import io.waterkite94.hd.hotdeal.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberJoinService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	private final AddressRepository addressRepository;
	private final AddressMapper addressMapper;

	private final VerificationCodeRedisAdapter verificationCodeRedisAdapter;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public String joinMember(Member member, Address address, String verificationCode) {
		validateVerificationCode(member.getEmail(), verificationCode);
		validateEmailAndPhoneNumber(member.getEmail(), member.getPhoneNumber());

		String memberId = memberRepository.save(
			memberMapper.toEntity(initializeMemberDomainForJoin(member))).getMemberId();

		addressRepository.save(
			addressMapper.toEntity(initializeAddressDomainForJoin(address, memberId)));

		return memberId;
	}

	private void validateVerificationCode(String email, String authenticationCode) {
		Optional<String> findVerificationCodeOptional = verificationCodeRedisAdapter.getVerificationCode(email);

		if (findVerificationCodeOptional.isEmpty() || !findVerificationCodeOptional.get().equals(authenticationCode)) {
			throw new IllegalArgumentException("Invalid email authentication code");
		}
	}

	private void validateEmailAndPhoneNumber(String email, String phoneNumber) {
		if (!memberRepository.findByEmailOrPhoneNumber(email, phoneNumber).isEmpty()) {
			throw new DuplicatedAccountException("Email Or Phone Number is already use");
		}
	}

	private Member initializeMemberDomainForJoin(Member member) {
		return member.initializeForJoin(
			createUuid(),
			encryptedPassword(member.getPassword())
		);
	}

	private static Address initializeAddressDomainForJoin(Address address, String memberId) {
		return address.initializeForJoin(memberId);
	}

	private String createUuid() {
		return UUID.randomUUID().toString();
	}

	private String encryptedPassword(String password) {
		return passwordEncoder.encode(password);
	}

}
