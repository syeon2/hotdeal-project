package io.waterkite94.hd.hotdeal.member.api.application;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.redis.VerificationCodeRedisAdapter;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.smtp.VerificationCodeMailAdapter;

class AuthenticationCodeServiceTest extends IntegrationTestSupport {

	@MockBean
	private VerificationCodeMailAdapter verificationCodeMailAdapter;

	@MockBean
	private VerificationCodeRedisAdapter verificationCodeRedisAdapter;

	@Autowired
	private AuthenticationCodeService authenticationCodeService;

	@Test
	@DisplayName(value = "이메일에 인증번호를 전송합니다.")
	void sendVerificationCodeToEmail() {
		// given
		String email = "test@example.com";

		// when
		authenticationCodeService.sendVerificationCodeToEmail(email);

		// then
		verify(verificationCodeRedisAdapter, times(1)).saveVerificationCode(eq(email), anyString());
		verify(verificationCodeMailAdapter, times(1)).sendVerificationCodeToEmail(eq(email), eq("회원가입 인증번호입니다."),
			anyString());

	}
}
