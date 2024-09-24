package io.waterkite94.hd.hotdeal.member.api.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

	private Long id;

	private String memberId;

	private String email;

	private String password;

	private String name;

	private String phoneNumber;

	private MemberRole role;

	@Builder
	private Member(Long id, String memberId, String email, String password, String name, String phoneNumber,
		MemberRole role) {
		this.id = id;
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public Member initializeForJoin(String uuid, String encryptedPassword) {
		return this
			.withMemberId(uuid)
			.withPassword(encryptedPassword)
			.withMemberRole(MemberRole.USER_NORMAL);
	}

	private Member withMemberId(String memberId) {
		return new Member(this.id, memberId, this.email, this.password, this.name, this.phoneNumber, this.role);
	}

	private Member withPassword(String password) {
		return new Member(this.id, getMemberId(), this.email, password, this.name, this.phoneNumber, this.role);
	}

	private Member withMemberRole(MemberRole role) {
		return new Member(this.id, getMemberId(), this.email, this.password, this.name, this.phoneNumber, role);
	}
}
