package io.waterkite94.hd.hotdeal.member.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Address {

	private Long id;

	private String city;

	private String state;

	private String zipcode;

	private String address;

	private String memberId;

	@Builder
	private Address(Long id, String city, String state, String zipcode, String address, String memberId) {
		this.id = id;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.address = address;
		this.memberId = memberId;
	}

	public Address initialize(String memberId) {
		return withMemberId(memberId);
	}

	private Address withMemberId(String memberId) {
		return new Address(this.id, this.city, this.state, this.zipcode, this.address, memberId);
	}
}
