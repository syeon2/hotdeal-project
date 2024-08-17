package io.waterkite94.hd.hotdeal.item.dao.custom;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.waterkite94.hd.hotdeal.item.dao.entity.QItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.ItemType;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemBoardDto;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.QMemberEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ItemBoardDto> searchItemsByCategoryId(Long categoryId, ItemType itemType, Long itemOffset) {
		BooleanExpression itemTypeCondition = new CaseBuilder()
			.when(QItemEntity.itemEntity.type.eq(ItemType.PRE_ORDER)).then(true)
			.otherwise(false);

		if (itemType.equals(ItemType.NONE)) {
			return queryFactory.select(Projections.constructor(ItemBoardDto.class,
					QItemEntity.itemEntity.id,
					QItemEntity.itemEntity.name,
					QItemEntity.itemEntity.price,
					QItemEntity.itemEntity.discount,
					QItemEntity.itemEntity.introduction,
					itemTypeCondition.as("isPreOrderItem"),
					QItemEntity.itemEntity.preOrderTime,
					QMemberEntity.memberEntity.name.as("sellerName"),
					QMemberEntity.memberEntity.memberId.as("sellerId")
				)).from(QItemEntity.itemEntity)
				.leftJoin(QMemberEntity.memberEntity)
				.on(QItemEntity.itemEntity.memberId.eq(QMemberEntity.memberEntity.memberId))
				.where(QItemEntity.itemEntity.categoryId.eq(categoryId))
				.offset(itemOffset)
				.limit(10)
				.fetch();
		} else if (itemType.equals(ItemType.PRE_ORDER)) {
			return queryFactory.select(Projections.constructor(ItemBoardDto.class,
					QItemEntity.itemEntity.id,
					QItemEntity.itemEntity.name,
					QItemEntity.itemEntity.price,
					QItemEntity.itemEntity.discount,
					QItemEntity.itemEntity.introduction,
					itemTypeCondition.as("isPreOrderItem"),
					QItemEntity.itemEntity.preOrderTime,
					QMemberEntity.memberEntity.name.as("sellerName"),
					QMemberEntity.memberEntity.memberId.as("sellerId")
				)).from(QItemEntity.itemEntity)
				.leftJoin(QMemberEntity.memberEntity)
				.on(QItemEntity.itemEntity.memberId.eq(QMemberEntity.memberEntity.memberId))
				.where(
					QItemEntity.itemEntity.categoryId.eq(categoryId),
					QItemEntity.itemEntity.type.eq(ItemType.PRE_ORDER)
				)
				.offset(itemOffset)
				.limit(10)
				.fetch();
		} else {
			return queryFactory.select(Projections.constructor(ItemBoardDto.class,
					QItemEntity.itemEntity.id,
					QItemEntity.itemEntity.name,
					QItemEntity.itemEntity.price,
					QItemEntity.itemEntity.discount,
					QItemEntity.itemEntity.introduction,
					itemTypeCondition.as("isPreOrderItem"),
					QItemEntity.itemEntity.preOrderTime,
					QMemberEntity.memberEntity.name.as("sellerName"),
					QMemberEntity.memberEntity.memberId.as("sellerId")
				)).from(QItemEntity.itemEntity)
				.leftJoin(QMemberEntity.memberEntity)
				.on(QItemEntity.itemEntity.memberId.eq(QMemberEntity.memberEntity.memberId))
				.where(
					QItemEntity.itemEntity.categoryId.eq(categoryId),
					QItemEntity.itemEntity.type.eq(ItemType.NORMAL_ORDER)
				)
				.offset(itemOffset)
				.limit(10)
				.fetch();
		}
	}
}
