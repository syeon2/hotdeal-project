package io.waterkite94.hd.hotdeal.item.dao.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.waterkite94.hd.hotdeal.item.dao.entity.QCategoryEntity;
import io.waterkite94.hd.hotdeal.item.dao.entity.QItemEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.FindAdminItemDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemBoardDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemStatus;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
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
				.orderBy(QItemEntity.itemEntity.id.desc())
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
				.orderBy(QItemEntity.itemEntity.id.desc())
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
				.orderBy(QItemEntity.itemEntity.id.desc())
				.fetch();
		}
	}

	@Override
	public List<ItemBoardDto> searchItemsContainsWord(String word, Long itemOffset) {
		BooleanExpression itemTypeCondition = new CaseBuilder()
			.when(QItemEntity.itemEntity.type.eq(ItemType.PRE_ORDER)).then(true)
			.otherwise(false);

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
			.where(QItemEntity.itemEntity.name.contains(word))
			.offset(itemOffset)
			.limit(10)
			.orderBy(QItemEntity.itemEntity.id.desc())
			.fetch();
	}

	@Override
	public ItemBoardDto findItemDetail(Long itemId) {
		BooleanExpression itemTypeCondition = new CaseBuilder()
			.when(QItemEntity.itemEntity.type.eq(ItemType.PRE_ORDER)).then(true)
			.otherwise(false);

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
			.where(QItemEntity.itemEntity.id.eq(itemId))
			.fetchFirst();
	}

	@Override
	public Page<FindAdminItemDto> findAdminItems(String memberId, Pageable pageable) {
		JPAQuery<FindAdminItemDto> contentQuery = queryFactory.select(Projections.constructor(FindAdminItemDto.class,
				QItemEntity.itemEntity.id.as("itemId"),
				QItemEntity.itemEntity.uuid.as("itemUuid"),
				QItemEntity.itemEntity.name.as("itemName"),
				QItemEntity.itemEntity.price,
				QItemEntity.itemEntity.discount,
				QItemEntity.itemEntity.type.as("itemType"),
				QItemEntity.itemEntity.preOrderTime.as("preOrderSchedule"),
				QItemEntity.itemEntity.createdAt,
				QCategoryEntity.categoryEntity.id.as("categoryId"),
				QCategoryEntity.categoryEntity.name.as("categoryName")
			)).from(QItemEntity.itemEntity)
			.leftJoin(QCategoryEntity.categoryEntity)
			.on(QItemEntity.itemEntity.categoryId.eq(QCategoryEntity.categoryEntity.id))
			.where(
				QItemEntity.itemEntity.memberId.eq(memberId),
				QItemEntity.itemEntity.status.eq(ItemStatus.ACTIVE)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		pageable.getSort().stream().forEach(sort -> {
			Order order = sort.isAscending() ? Order.ASC : Order.DESC;
			String property = sort.getProperty();

			Path<Object> target = Expressions.path(Object.class, QItemEntity.itemEntity, property);
			OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, target);
			contentQuery.orderBy(orderSpecifier);
		});

		Long totalItemCount = queryFactory.select(QItemEntity.itemEntity.count())
			.from(QItemEntity.itemEntity)
			.where(QItemEntity.itemEntity.memberId.eq(memberId))
			.fetchOne();

		return new PageImpl<>(contentQuery.fetch(), pageable, totalItemCount);
	}

}
