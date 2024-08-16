package io.waterkite94.hd.hotdeal.member.service;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.member.dao.redis.VerificationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.dao.smtp.VerificationCodeMailAdapter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationCodeService {

	private final VerificationCodeMailAdapter verificationCodeMailAdapter;
	private final VerificationCodeRedisAdapter verificationCodeRedisAdapter;

	private static final SecureRandom RANDOM = new SecureRandom();
	private static final String TITLE = "회원가입 인증번호입니다.";
	private static final Integer CODE_LENGTH = 10;

	public void sendVerificationCodeToEmail(String toEmail) {
		String verificationCode = createVerificationCode();

		verificationCodeRedisAdapter.saveVerificationCode(toEmail, verificationCode);
		verificationCodeMailAdapter.sendVerificationCodeToEmail(toEmail, TITLE, verificationCode);
	}

	private String createVerificationCode() {
		return IntStream.range(0, CODE_LENGTH)
			.mapToObj(idx -> String.valueOf(RANDOM.nextInt(10)))
			.collect(Collectors.joining());
	}
}
