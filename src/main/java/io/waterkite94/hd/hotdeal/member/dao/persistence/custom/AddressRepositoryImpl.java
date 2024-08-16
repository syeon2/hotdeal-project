package io.waterkite94.hd.hotdeal.member.dao.persistence.custom;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.QAddressEntity;
import io.waterkite94.hd.hotdeal.member.domain.Address;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Transactional
	@Modifying(clearAutomatically = true)
	@Override
	public void updateAddress(String memberId, Address address) {
		queryFactory.update(QAddressEntity.addressEntity)
			.set(QAddressEntity.addressEntity.city, address.getCity())
			.set(QAddressEntity.addressEntity.state, address.getState())
			.set(QAddressEntity.addressEntity.address, address.getAddress())
			.set(QAddressEntity.addressEntity.zipcode, address.getZipcode())
			.where(QAddressEntity.addressEntity.memberId.eq(memberId))
			.execute();
	}
}
