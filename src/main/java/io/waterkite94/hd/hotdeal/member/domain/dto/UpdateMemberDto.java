package io.waterkite94.hd.hotdeal.member.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMemberDto {

	private String name;

	private String phoneNumber;

	@Builder
	private UpdateMemberDto(String name, String phoneNumber) {
		;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
}
