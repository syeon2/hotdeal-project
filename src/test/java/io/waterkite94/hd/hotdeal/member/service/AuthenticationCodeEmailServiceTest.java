package io.waterkite94.hd.hotdeal.member.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.member.dao.redis.AuthenticationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.dao.smtp.MailSmtp;

class AuthenticationCodeEmailServiceTest extends IntegrationTestSupport {

	@MockBean
	private MailSmtp mailSmtp;

	@MockBean
	private AuthenticationCodeRedisAdapter authenticationCodeRedisAdapter;

	@Autowired
	private AuthenticationCodeEmailService authenticationCodeEmailService;

	@Test
	@DisplayName(value = "이메일에 인증번호를 전송합니다.")
	void sendAuthenticationCodeToEmail() {
		// given
		String email = "test@example.com";

		// when
		authenticationCodeEmailService.sendAuthenticationCodeToEmail(email);

		// then
		verify(authenticationCodeRedisAdapter, times(1)).saveAuthenticationCode(eq(email), anyString());
		verify(mailSmtp, times(1)).sendEmail(eq(email), eq("회원가입 인증번호입니다."), anyString());

	}
}
