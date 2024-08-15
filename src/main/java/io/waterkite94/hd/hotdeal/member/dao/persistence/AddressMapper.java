package io.waterkite94.hd.hotdeal.member.dao.persistence;

import org.springframework.stereotype.Component;

import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.AddressEntity;
import io.waterkite94.hd.hotdeal.member.domain.Address;

@Component
public class AddressMapper {

	public AddressEntity toEntity(Address address) {
		return AddressEntity.builder()
			.city(address.getCity())
			.state(address.getState())
			.address(address.getAddress())
			.zipcode(address.getZipcode())
			.memberId(address.getMemberId())
			.build();
	}

	public Address toDomain(AddressEntity entity) {
		return Address.builder()
			.id(entity.getId())
			.city(entity.getCity())
			.state(entity.getState())
			.address(entity.getAddress())
			.zipcode(entity.getZipcode())
			.memberId(entity.getMemberId())
			.build();
	}
}
