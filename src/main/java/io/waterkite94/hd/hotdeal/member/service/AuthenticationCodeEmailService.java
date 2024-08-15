package io.waterkite94.hd.hotdeal.member.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.member.dao.redis.AuthenticationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.dao.smtp.MailSmtp;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationCodeEmailService {

	private final MailSmtp mailSmtp;
	private final AuthenticationCodeRedisAdapter authenticationCodeRedisAdapter;

	private static final String TITLE = "회원가입 인증번호입니다.";
	private static final Integer CODE_LENGTH = 6;

	public void sendAuthenticationCodeToEmail(String toEmail) {
		String authenticationCode = createAuthenticationCode();
		authenticationCodeRedisAdapter.saveAuthenticationCode(toEmail, authenticationCode);

		mailSmtp.sendEmail(toEmail, TITLE, authenticationCode);
	}

	private String createAuthenticationCode() {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < CODE_LENGTH; i++) {
			sb.append(random.nextInt(10));
		}

		return sb.toString();
	}
}
