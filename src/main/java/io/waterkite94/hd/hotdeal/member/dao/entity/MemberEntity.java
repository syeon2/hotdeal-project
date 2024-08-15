package io.waterkite94.hd.hotdeal.member.dao.entity;

import io.waterkite94.hd.hotdeal.util.wrapper.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private Long id;

	@Column(name = "memberId", columnDefinition = "varchar(60)", nullable = false, unique = true)
	private String memberId;

	@Column(name = "email", columnDefinition = "varchar(255)", nullable = false, unique = true)
	private String email;

	@Column(name = "password", columnDefinition = "varchar(60)", nullable = false)
	private String password;

	@Column(name = "name", columnDefinition = "varchar(60)", nullable = false)
	private String name;

	@Column(name = "phone_number", columnDefinition = "varchar(30)", nullable = false, unique = true)
	private String phoneNumber;

	@Builder
	private MemberEntity(String memberId, String email, String password, String name, String phoneNumber) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
}
