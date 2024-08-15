package io.waterkite94.hd.hotdeal.member.web.api.response;

import lombok.Getter;

@Getter
public class CreateMemberResponse {

	private String memberId;

	public CreateMemberResponse(String memberId) {
		this.memberId = memberId;
	}
}
