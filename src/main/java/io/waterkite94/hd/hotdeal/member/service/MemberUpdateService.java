package io.waterkite94.hd.hotdeal.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.DuplicatedAccountException;
import io.waterkite94.hd.hotdeal.member.dao.persistence.AddressRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.AddressEntity;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.MemberEntity;
import io.waterkite94.hd.hotdeal.member.dao.redis.VerificationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.domain.Address;
import io.waterkite94.hd.hotdeal.member.domain.dto.UpdateMemberDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

	private final MemberRepository memberRepository;
	private final AddressRepository addressRepository;

	private final VerificationCodeRedisAdapter verificationCodeRedisAdapter;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void updateMemberInfo(String memberId, UpdateMemberDto updateMemberDto, Address address) {
		MemberEntity findMember = findMemberEntity(memberId);
		validatePhoneNumber(findMember.getPhoneNumber(), updateMemberDto.getPhoneNumber());

		AddressEntity findAddress = findAddressEntity(memberId);

		findMember.changeInfo(updateMemberDto.getName(), updateMemberDto.getPhoneNumber());
		findAddress.changeAddress(address);
	}

	@Transactional
	public void updateMemberEmail(String memberId, String email, String verificationCode) {
		MemberEntity findMember = findMemberEntity(memberId);
		validateEmail(email);

		validateVerificationCode(email, verificationCode);

		findMember.changeEmail(email);
	}

	@Transactional
	public void updateMemberPassword(String memberId, String currentPassword, String newPassword) {
		MemberEntity findMember = findMemberEntity(memberId);

		validatePassword(currentPassword, findMember.getPassword());

		findMember.changePassword(encryptedPassword(newPassword));
	}

	private MemberEntity findMemberEntity(String memberId) {
		Optional<MemberEntity> findMemberOptional = memberRepository.findByMemberId(memberId);

		if (findMemberOptional.isEmpty()) {
			throw new IllegalArgumentException("Member not found");
		}

		return findMemberOptional.get();
	}

	private AddressEntity findAddressEntity(String memberId) {
		Optional<AddressEntity> findAddressOptional = addressRepository.findByMemberId(memberId);

		if (findAddressOptional.isEmpty()) {
			throw new IllegalArgumentException("Address not found");
		}

		return findAddressOptional.get();
	}

	private void validatePassword(String currentPassword, String storedPassword) {
		if (!passwordEncoder.matches(currentPassword, storedPassword)) {
			throw new IllegalArgumentException("Wrong Password");
		}
	}

	private void validatePhoneNumber(String currentPhoneNumber, String changePhoneNumber) {
		if (currentPhoneNumber.equals(changePhoneNumber)
			|| memberRepository.findByPhoneNumber(changePhoneNumber).isEmpty()
		) {
			return;
		}

		throw new DuplicatedAccountException("Phone number already in use");
	}

	private void validateEmail(String email) {
		if (!memberRepository.findByEmail(email).isEmpty()) {
			throw new IllegalArgumentException("Email already exists");
		}
	}

	private String encryptedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	private void validateVerificationCode(String email, String authenticationCode) {
		Optional<String> findVerificationCodeOptional = verificationCodeRedisAdapter.getVerificationCode(email);

		if (findVerificationCodeOptional.isEmpty() || !findVerificationCodeOptional.get().equals(authenticationCode)) {
			throw new IllegalArgumentException("Invalid email authentication code");
		}
	}
}
