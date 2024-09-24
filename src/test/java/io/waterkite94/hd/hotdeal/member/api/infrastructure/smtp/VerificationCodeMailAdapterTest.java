package io.waterkite94.hd.hotdeal.member.api.infrastructure.smtp;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;

class VerificationCodeMailAdapterTest extends IntegrationTestSupport {

	@MockBean
	private JavaMailSender mailSender;

	@Autowired
	private VerificationCodeMailAdapter verificationCodeMailAdapter;

	@Test
	@DisplayName(value = "이메일을 보냅니다.")
	void sendVerificationCodeToEmail() {
		// given
		String toEmail = "test@example.com";
		String title = "title";
		String content = "content";

		doNothing().when(mailSender).send(any(SimpleMailMessage.class));

		// when
		verificationCodeMailAdapter.sendVerificationCodeToEmail(toEmail, title, content);

		// then
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
	}
}
