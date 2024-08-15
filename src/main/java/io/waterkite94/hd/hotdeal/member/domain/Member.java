package io.waterkite94.hd.hotdeal.member.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

	private Long id;

	record MemberId(String memberId) {
	}

	private MemberId memberId;

	private String email;

	private String password;

	private String name;

	private String phoneNumber;

	@Builder
	private Member(Long id, String memberId, String email, String name, String phoneNumber) {
		this.id = id;
		this.memberId = new MemberId(memberId);
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public void createMemberId(String memberId) {
		this.memberId = new MemberId(memberId);
	}

	public String getMemberId() {
		return memberId.memberId();
	}
}
