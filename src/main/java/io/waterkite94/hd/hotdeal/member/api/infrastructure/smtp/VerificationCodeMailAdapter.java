package io.waterkite94.hd.hotdeal.member.api.infrastructure.smtp;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificationCodeMailAdapter {

	private final JavaMailSender mailSender;

	public void sendVerificationCodeToEmail(String toEmail, String title, String verificationCode) {
		SimpleMailMessage emailForm = createMessageForm(toEmail, title, verificationCode);

		mailSender.send(emailForm);
	}

	private SimpleMailMessage createMessageForm(String toEmail, String title, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(title);
		message.setText(content);

		return message;
	}
}
