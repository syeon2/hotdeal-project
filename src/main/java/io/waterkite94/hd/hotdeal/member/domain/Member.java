package io.waterkite94.hd.hotdeal.member.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

	private Long id;

	@Getter
	static class MemberId {

		private String memberId;

		public MemberId(String memberId) {
			this.memberId = memberId;
		}
	}

	private MemberId memberId;

	private String email;

	private String password;

	private String name;

	private String phoneNumber;

	private MemberRole role;

	@Builder
	private Member(Long id, String memberId, String email, String password, String name, String phoneNumber,
		MemberRole role) {
		this.id = id;
		this.memberId = new MemberId(memberId);
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public void createMemberId(String memberId) {
		this.memberId = new MemberId(memberId);
	}

	public String getMemberId() {
		return memberId.getMemberId();
	}

	public Member initializeMember(String memberId, String password) {
		return this
			.withMemberId(memberId)
			.withPassword(password)
			.withRole(MemberRole.USER_NORMAL);
	}

	private Member withMemberId(String memberId) {
		return new Member(this.id, memberId, this.email, this.password, this.name, this.phoneNumber, this.role);
	}

	private Member withPassword(String password) {
		return new Member(this.id, getMemberId(), this.email, password, this.name, this.phoneNumber, this.role);
	}

	private Member withRole(MemberRole role) {
		return new Member(this.id, getMemberId(), this.email, this.password, this.name, this.phoneNumber, role);
	}
}
