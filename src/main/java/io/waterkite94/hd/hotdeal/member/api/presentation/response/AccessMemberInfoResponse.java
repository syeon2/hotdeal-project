package io.waterkite94.hd.hotdeal.member.api.presentation.response;

import io.waterkite94.hd.hotdeal.member.api.domain.MemberRole;
import io.waterkite94.hd.hotdeal.member.api.domain.dto.AccessMemberDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessMemberInfoResponse {

	private String memberId;

	private String email;

	private String name;

	private String phoneNumber;

	private MemberRole role;

	private AddressResponse address;

	@Builder
	private AccessMemberInfoResponse(String memberId, String email, String name, String phoneNumber, MemberRole role,
		String city, String state, String address, String zipcode) {
		this.memberId = memberId;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.address = AddressResponse.toAddressResponse(city, state, address, zipcode);
	}

	public static AccessMemberInfoResponse of(AccessMemberDto accessMemberDto) {
		return AccessMemberInfoResponse.builder()
			.memberId(accessMemberDto.getMemberId())
			.email(accessMemberDto.getEmail())
			.name(accessMemberDto.getName())
			.phoneNumber(accessMemberDto.getPhoneNumber())
			.role(accessMemberDto.getRole())
			.city(accessMemberDto.getCity())
			.state(accessMemberDto.getState())
			.address(accessMemberDto.getAddress())
			.zipcode(accessMemberDto.getZipcode())
			.build();
	}

	@Getter
	@Builder
	private static class AddressResponse {

		private String city;

		private String state;

		private String address;

		private String zipcode;

		public static AddressResponse toAddressResponse(String city, String state, String address, String zipcode) {
			return AddressResponse.builder()
				.city(city)
				.state(state)
				.address(address)
				.zipcode(zipcode)
				.build();
		}
	}
}
