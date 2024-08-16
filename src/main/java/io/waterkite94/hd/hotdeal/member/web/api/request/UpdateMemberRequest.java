package io.waterkite94.hd.hotdeal.member.web.api.request;

import io.waterkite94.hd.hotdeal.member.domain.Address;
import io.waterkite94.hd.hotdeal.member.domain.dto.UpdateMemberDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMemberRequest {

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

	@Builder
	private UpdateMemberRequest(String name, String phoneNumber, String city, String state, String address,
		String zipcode) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.state = state;
		this.address = address;
		this.zipcode = zipcode;
	}

	public UpdateMemberDto toUpdateMemberDto() {
		return UpdateMemberDto.builder()
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
}
