package io.waterkite94.hd.hotdeal.member.dao.smtp;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailSmtp {

	private final JavaMailSender mailSender;

	public void sendEmail(String toEmail, String title, String content) {
		SimpleMailMessage emailForm = createMessageForm(toEmail, title, content);

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
