package io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.custom;

import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.waterkite94.hd.hotdeal.member.api.domain.dto.AccessMemberDto;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.entity.QAddressEntity;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.entity.QMemberEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<AccessMemberDto> findMemberWithAddress(String memberId) {
		AccessMemberDto findMember = queryFactory.select(
				Projections.constructor(AccessMemberDto.class,
					QMemberEntity.memberEntity.memberId,
					QMemberEntity.memberEntity.email,
					QMemberEntity.memberEntity.name,
					QMemberEntity.memberEntity.phoneNumber,
					QMemberEntity.memberEntity.role,
					QAddressEntity.addressEntity.city,
					QAddressEntity.addressEntity.state,
					QAddressEntity.addressEntity.address,
					QAddressEntity.addressEntity.zipcode
				)
			)
			.from(QMemberEntity.memberEntity)
			.leftJoin(QAddressEntity.addressEntity)
			.on(QMemberEntity.memberEntity.memberId.eq(QAddressEntity.addressEntity.memberId))
			.where(QMemberEntity.memberEntity.memberId.eq(memberId))
			.fetchFirst();

		return Optional.ofNullable(findMember);
	}
}
