package io.waterkite94.hd.hotdeal.item.dao.custom;

import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.waterkite94.hd.hotdeal.item.dao.entity.QInquiryCommentEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.InquiryCommentDto;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.QMemberEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InquiryCommentRepositoryImpl implements InquiryCommentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<InquiryCommentDto> findInquiryCommentDto(Long itemInquiryId) {
		InquiryCommentDto findDto = queryFactory.select(Projections.constructor(InquiryCommentDto.class,
				QInquiryCommentEntity.inquiryCommentEntity.id.as("inquiryCommentId"),
				QInquiryCommentEntity.inquiryCommentEntity.comment,
				QInquiryCommentEntity.inquiryCommentEntity.createdAt,
				QInquiryCommentEntity.inquiryCommentEntity.updatedAt,
				QInquiryCommentEntity.inquiryCommentEntity.memberId,
				QMemberEntity.memberEntity.name.as("memberName")
			)).from(QInquiryCommentEntity.inquiryCommentEntity)
			.leftJoin(QMemberEntity.memberEntity)
			.on(QInquiryCommentEntity.inquiryCommentEntity.memberId.eq(QMemberEntity.memberEntity.memberId))
			.where(QInquiryCommentEntity.inquiryCommentEntity.itemInquiry.id.eq(itemInquiryId))
			.fetchOne();

		return Optional.ofNullable(findDto);
	}
}
