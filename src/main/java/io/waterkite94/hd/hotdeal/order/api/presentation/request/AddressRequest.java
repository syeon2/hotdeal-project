package io.waterkite94.hd.hotdeal.order.api.presentation.request;

import io.waterkite94.hd.hotdeal.order.api.domain.dto.AddAddressDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressRequest {

	@NotBlank(message = "도시는 빈칸을 허용하지 않습니다. ex) Seoul - si")
	private String city;

	@NotBlank(message = "도는 빈칸을 허용하지 않습니다. ex) Gyeonggi - do")
	private String state;

	@NotBlank(message = "우편번호는 빈칸을 허용하지 않습니다.")
	private String zipcode;

	@NotBlank(message = "세부주소는 빈칸을 허용하지 않습니다.")
	private String address;

	@Builder
	private AddressRequest(String city, String state, String zipcode, String address) {
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.address = address;
	}

	public AddAddressDto toServiceDto() {
		return AddAddressDto.of(city, state, zipcode, address);
	}
}
