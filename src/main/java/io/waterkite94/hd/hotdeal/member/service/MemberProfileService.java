package io.waterkite94.hd.hotdeal.member.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.member.dao.AddressMapper;
import io.waterkite94.hd.hotdeal.member.dao.AddressRepository;
import io.waterkite94.hd.hotdeal.member.dao.MemberMapper;
import io.waterkite94.hd.hotdeal.member.dao.MemberRepository;
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

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public String createMember(Member member, Address address) {
		// TODO: Check Email Authentication Code

		validateEmailAndPhoneNumber(member.getEmail(), member.getPhoneNumber());

		Member initedMember = member.initializeMember(
			createUuid(),
			encryptedPassword(member.getPassword())
		);

		memberRepository.save(memberMapper.toEntity(initedMember));
		addressRepository.save(addressMapper.toEntity(address));

		return initedMember.getMemberId();
	}

	private void validateEmailAndPhoneNumber(String email, String phoneNumber) {
		if (!memberRepository.findByEmailAndPhoneNumber(email, phoneNumber).isEmpty()) {
			throw new IllegalArgumentException("Email And Phone Number is already use");
		}
	}

	private String createUuid() {
		return UUID.randomUUID().toString();
	}

	private String encryptedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
