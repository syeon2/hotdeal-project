package io.waterkite94.hd.hotdeal.order.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddAddressDto {

	private final String city;
	private final String state;
	private final String zipcode;
	private final String address;

	@Builder
	private AddAddressDto(String city, String state, String zipcode, String address) {
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.address = address;
	}

	public static AddAddressDto of(String city, String state, String zipcode, String address) {
		return new AddAddressDto(city, state, zipcode, address);
	}
}
