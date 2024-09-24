package io.waterkite94.hd.hotdeal.member.api.domain;

import lombok.Getter;

@Getter
public enum MemberRole {
	USER_NORMAL("일반 유저"),
	USER_VIP("VIP 유저"),
	ADMIN_NORMAL("일반 관리자"),
	ADMIN_SUPER("슈퍼 관리자");

	private final String name;

	MemberRole(String name) {
		this.name = name;
	}
}
