package io.waterkite94.hd.hotdeal.member.api.presentation.response;

import lombok.Getter;

@Getter
public class JoinMemberResponse {

	private String memberId;

	public JoinMemberResponse(String memberId) {
		this.memberId = memberId;
	}
}
