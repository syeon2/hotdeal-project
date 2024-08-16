package io.waterkite94.hd.hotdeal.member.web.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateEmailRequest {

	@Email(message = "이메일은 이메일 형식으로 입력해야합니다..")
	@NotBlank(message = "이메일은 빈칸을 허용하지 않습니다.")
	private String email;

	@NotBlank(message = "이메일 인증코드는 빈칸을 허용하지 않습니다.")
	private String verificationCode;

	public UpdateEmailRequest(String email, String verificationCode) {
		this.email = email;
		this.verificationCode = verificationCode;
	}
}
