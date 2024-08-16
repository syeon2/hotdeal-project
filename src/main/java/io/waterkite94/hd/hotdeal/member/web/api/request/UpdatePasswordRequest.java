package io.waterkite94.hd.hotdeal.member.web.api.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

	@NotBlank(message = "oldPassword는 빈칸을 허용하지 않습니다.")
	@Length(min = 8, max = 20, message = "비밀번호는 8 ~ 20자리입니다.")
	private String oldPassword;

	@NotBlank(message = "oldPassword는 빈칸을 허용하지 않습니다.")
	@Length(min = 8, max = 20, message = "비밀번호는 8 ~ 20자리입니다.")
	private String newPassword;

	public UpdatePasswordRequest(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
}
