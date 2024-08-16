package io.waterkite94.hd.hotdeal.member.dao.persistence.custom;

import io.waterkite94.hd.hotdeal.member.domain.Address;

public interface AddressRepositoryCustom {

	void updateAddress(String memberId, Address address);
}
