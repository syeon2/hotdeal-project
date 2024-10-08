package io.waterkite94.hd.hotdeal.member.api.presentation.request;

import org.hibernate.validator.constraints.Length;

import io.waterkite94.hd.hotdeal.member.api.domain.Address;
import io.waterkite94.hd.hotdeal.member.api.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinMemberRequest {

	@Email(message = "이메일은 이메일 형식으로 입력해야합니다..")
	@NotBlank(message = "이메일은 빈칸을 허용하지 않습니다.")
	private String email;

	@NotBlank(message = "비밀번호는 빈칸을 허용하지 않습니다.")
	@Length(min = 8, max = 20, message = "비밀번호는 8 ~ 20자리입니다.")
	private String password;

	@NotBlank(message = "이름은 빈칸을 허용하지 않습니다.")
	private String name;

	@NotBlank(message = "전화번호는 빈칸을 허용하지 않습니다.")
	@Pattern(regexp = "[0-9]{10,11}", message = "10 ~ 11자리의 숫자만 입력 가능합니다.")
	private String phoneNumber;

	@NotBlank(message = "city는 빈칸을 허용하지 않습니다.")
	private String city;

	@NotBlank(message = "state는 빈칸을 허용하지 않습니다.")
	private String state;

	@NotBlank(message = "address는 빈칸을 허용하지 않습니다.")
	private String address;

	@NotBlank(message = "zipcode는 빈칸을 허용하지 않습니다.")
	private String zipcode;

	@NotBlank(message = "이메일 인증코드는 빈칸을 허용하지 않습니다.")
	private String verificationCode;

	public Member toMemberDomain() {
		return Member.builder()
			.email(email)
			.password(password)
			.name(name)
			.phoneNumber(phoneNumber)
			.build();
	}

	public Address toAddressDomain() {
		return Address.builder()
			.city(city)
			.state(state)
			.address(address)
			.zipcode(zipcode)
			.build();
	}

	@Builder
	private JoinMemberRequest(String email, String password, String name, String phoneNumber, String city,
		String state,
		String address, String zipcode, String verificationCode) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.state = state;
		this.address = address;
		this.zipcode = zipcode;
		this.verificationCode = verificationCode;
	}
}
