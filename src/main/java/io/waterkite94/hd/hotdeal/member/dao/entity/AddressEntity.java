package io.waterkite94.hd.hotdeal.member.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	// 도시 이름 : ex) Seoul - si
	@Column(name = "city", columnDefinition = "varchar(255)", nullable = false)
	private String city;

	// 도 이름 : Gyeongi - do 광역 시 중복
	@Column(name = "street", columnDefinition = "varchar(255)", nullable = false)
	private String state;

	// 우편 번호
	@Column(name = "zipcode", columnDefinition = "varchar(255)", nullable = false)
	private String zipcode;

	// 세부 주소 : 읍, 면, 동
	@Column(name = "address", columnDefinition = "varchar(255)", nullable = false)
	private String address;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity member;

	@Builder
	private AddressEntity(String city, String state, String zipcode, String address) {
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.address = address;
	}
}
