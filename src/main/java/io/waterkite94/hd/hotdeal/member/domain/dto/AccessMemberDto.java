package io.waterkite94.hd.hotdeal.member.domain.dto;

import io.waterkite94.hd.hotdeal.member.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessMemberDto {

	private String memberId;

	private String email;

	private String name;

	private String phoneNumber;

	private MemberRole role;

	private String city;

	private String state;

	private String address;

	private String zipcode;

	@Builder
	public AccessMemberDto(String memberId, String email, String name, String phoneNumber, MemberRole role,
		String city,
		String state, String address, String zipcode) {
		this.memberId = memberId;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.city = city;
		this.state = state;
		this.address = address;
		this.zipcode = zipcode;
	}
}
