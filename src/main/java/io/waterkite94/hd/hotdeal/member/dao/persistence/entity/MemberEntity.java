package io.waterkite94.hd.hotdeal.member.dao.persistence.entity;

import io.waterkite94.hd.hotdeal.common.wrapper.BaseEntity;
import io.waterkite94.hd.hotdeal.member.domain.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	@Column(name = "member_id", columnDefinition = "varchar(60)", nullable = false, unique = true)
	private String memberId;

	@Column(name = "email", columnDefinition = "varchar(255)", nullable = false, unique = true)
	private String email;

	@Column(name = "password", columnDefinition = "varchar(60)", nullable = false)
	private String password;

	@Column(name = "name", columnDefinition = "varchar(60)", nullable = false)
	private String name;

	@Column(name = "phone_number", columnDefinition = "varchar(30)", nullable = false, unique = true)
	private String phoneNumber;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role", columnDefinition = "varchar(30)", nullable = false)
	private MemberRole role;

	@Builder
	private MemberEntity(String memberId, String email, String password, String name, String phoneNumber,
		MemberRole role) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public void changeMemberInfo(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public void changeMemberEmail(String email) {
		this.email = email;
	}

	public void changeMemberPassword(String password) {
		this.password = password;
	}
}
