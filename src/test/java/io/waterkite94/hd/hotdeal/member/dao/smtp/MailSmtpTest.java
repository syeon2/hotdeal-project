package io.waterkite94.hd.hotdeal.member.dao.smtp;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;

class MailSmtpTest extends IntegrationTestSupport {

	@MockBean
	private JavaMailSender mailSender;

	@Autowired
	private MailSmtp mailSmtp;

	@Test
	@DisplayName(value = "이메일을 보냅니다.")
	void sendEmail() {
		// given
		String toEmail = "test@example.com";
		String title = "title";
		String content = "content";

		doNothing().when(mailSender).send(any(SimpleMailMessage.class));

		// when
		mailSmtp.sendEmail(toEmail, title, content);

		// then
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
	}
}
