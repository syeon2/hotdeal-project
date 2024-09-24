package io.waterkite94.hd.hotdeal.member.api.application;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.member.api.domain.Address;
import io.waterkite94.hd.hotdeal.member.api.domain.Member;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.AddressMapper;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.MemberMapper;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.redis.VerificationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.support.error.exception.DuplicatedAccountException;
import io.waterkite94.hd.hotdeal.support.util.UuidUtil;
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
			UuidUtil.createUuid(),
			encryptedPassword(member.getPassword())
		);
	}

	private static Address initializeAddressDomainForJoin(Address address, String memberId) {
		return address.initializeForJoin(memberId);
	}

	private String encryptedPassword(String password) {
		return passwordEncoder.encode(password);
	}

}
