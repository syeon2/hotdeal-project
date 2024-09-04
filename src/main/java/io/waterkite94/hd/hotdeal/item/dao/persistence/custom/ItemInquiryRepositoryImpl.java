package io.waterkite94.hd.hotdeal.item.dao.persistence.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.waterkite94.hd.hotdeal.item.dao.persistence.entity.QItemInquiryEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemInquiriesDto;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.QMemberEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemInquiryRepositoryImpl implements ItemInquiryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<RetrieveItemInquiriesDto> findItemInquiriesByItemId(Long itemId, Long offset) {
		return queryFactory.select(Projections.constructor(RetrieveItemInquiriesDto.class,
				QItemInquiryEntity.itemInquiryEntity.id.as("itemInquiryId"),
				QItemInquiryEntity.itemInquiryEntity.comment,
				QItemInquiryEntity.itemInquiryEntity.createdAt,
				QItemInquiryEntity.itemInquiryEntity.updatedAt,
				QMemberEntity.memberEntity.name.as("memberName")
			)).from(QItemInquiryEntity.itemInquiryEntity)
			.leftJoin(QMemberEntity.memberEntity)
			.on(QItemInquiryEntity.itemInquiryEntity.memberId.eq(QMemberEntity.memberEntity.memberId))
			.where(
				QItemInquiryEntity.itemInquiryEntity.item.id.eq(itemId),
				offset != 0L ? QItemInquiryEntity.itemInquiryEntity.id.lt(offset) : null
			)
			.limit(10)
			.orderBy(
				QItemInquiryEntity.itemInquiryEntity.createdAt.desc(),
				QItemInquiryEntity.itemInquiryEntity.id.desc()
			)
			.fetch();
	}

	@Override
	public List<Long> findItemInquiriesForToday(String memberId, Long itemId) {

		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = today.atStartOfDay();
		LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

		return queryFactory.select(QItemInquiryEntity.itemInquiryEntity.id)
			.from(QItemInquiryEntity.itemInquiryEntity)
			.where(
				QItemInquiryEntity.itemInquiryEntity.memberId.eq(memberId),
				QItemInquiryEntity.itemInquiryEntity.item.id.eq(itemId),
				QItemInquiryEntity.itemInquiryEntity.createdAt.between(startOfDay, endOfDay))
			.fetch();
	}
}
