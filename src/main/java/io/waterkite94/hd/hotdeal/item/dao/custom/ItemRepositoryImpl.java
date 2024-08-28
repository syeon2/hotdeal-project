package io.waterkite94.hd.hotdeal.item.dao.custom;

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
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveRegisteredItemDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemId;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemStatus;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.member.dao.persistence.entity.QMemberEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<RetrieveRegisteredItemDto> findAdminItemsByMemberId(String memberId, Pageable pageable) {
		JPAQuery<RetrieveRegisteredItemDto> contentQuery = queryFactory.select(Projections.constructor(
				RetrieveRegisteredItemDto.class,
				Projections.constructor(ItemId.class,
					QItemEntity.itemEntity.id.as("id"),
					QItemEntity.itemEntity.uuid.as("uuid")
				),
				QItemEntity.itemEntity.name.as("itemName"),
				Projections.constructor(Cost.class,
					QItemEntity.itemEntity.price.as("price"),
					QItemEntity.itemEntity.discount.as("discount")
				),
				QItemEntity.itemEntity.type.as("itemType"),
				QItemEntity.itemEntity.preOrderTime.as("preOrderSchedule"),
				QItemEntity.itemEntity.quantity,
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
			String property = sort.getProperty().equals("name") ? "name" : "createdAt";

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

	@Override
	public Page<RetrieveItemsDto> searchItemsByCategoryId(Long categoryId, ItemType type, String search,
		Pageable pageable) {
		BooleanExpression itemTypeCondition = new CaseBuilder()
			.when(QItemEntity.itemEntity.type.eq(ItemType.PRE_ORDER)).then(true)
			.otherwise(false);

		JPAQuery<RetrieveItemsDto> contentQuery = queryFactory.select(
				Projections.constructor(RetrieveItemsDto.class,
					QItemEntity.itemEntity.id.as("itemId"),
					QItemEntity.itemEntity.name.as("itemName"),
					Projections.constructor(Cost.class,
						QItemEntity.itemEntity.price,
						QItemEntity.itemEntity.discount
					),
					itemTypeCondition.as("isPreOrderItem"),
					QItemEntity.itemEntity.quantity,
					QItemEntity.itemEntity.preOrderTime,
					QMemberEntity.memberEntity.memberId.as("sellerId"),
					QMemberEntity.memberEntity.name.as("sellerName")
				)).from(QItemEntity.itemEntity)
			.leftJoin(QMemberEntity.memberEntity)
			.on(QItemEntity.itemEntity.memberId.eq(QMemberEntity.memberEntity.memberId))
			.where(
				QItemEntity.itemEntity.categoryId.eq(categoryId),
				QItemEntity.itemEntity.status.eq(ItemStatus.ACTIVE),
				type.equals(ItemType.PRE_ORDER) ? QItemEntity.itemEntity.type.eq(ItemType.PRE_ORDER)
					: type.equals(ItemType.NORMAL_ORDER) ? QItemEntity.itemEntity.type.eq(ItemType.NORMAL_ORDER)
					: null,
				search != null ? QItemEntity.itemEntity.name.contains(search) : null
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		pageable.getSort().stream().forEach(sort -> {
			Order order = sort.isAscending() ? Order.ASC : Order.DESC;
			String property =
				(sort.getProperty().equals("name") || sort.getProperty().equals("createdAt") || sort.getProperty()
					.equals("price")) ? sort.getProperty() : "createdAt";

			Path<Object> target = Expressions.path(Object.class, QItemEntity.itemEntity, property);
			OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, target);
			contentQuery.orderBy(orderSpecifier);
		});

		Long totalItemCount = queryFactory.select(QItemEntity.itemEntity.count())
			.from(QItemEntity.itemEntity)
			.where(QItemEntity.itemEntity.categoryId.eq(categoryId))
			.fetchOne();

		return new PageImpl<>(contentQuery.fetch(), pageable, totalItemCount);
	}

	@Override
	public ItemDetailDto findItemDetail(Long itemId) {
		BooleanExpression itemTypeCondition = new CaseBuilder()
			.when(QItemEntity.itemEntity.type.eq(ItemType.PRE_ORDER)).then(true)
			.otherwise(false);

		return queryFactory.select(Projections.constructor(ItemDetailDto.class,
				QItemEntity.itemEntity.id.as("itemId"),
				QItemEntity.itemEntity.name.as("itemName"),
				Projections.constructor(Cost.class,
					QItemEntity.itemEntity.price,
					QItemEntity.itemEntity.discount
				),
				QItemEntity.itemEntity.introduction,
				itemTypeCondition.as("isPreOrderItem"),
				QItemEntity.itemEntity.preOrderTime,
				QItemEntity.itemEntity.createdAt.as("createdAt"),
				QMemberEntity.memberEntity.name.as("sellerName"),
				QMemberEntity.memberEntity.memberId.as("sellerId")
			)).from(QItemEntity.itemEntity)
			.leftJoin(QMemberEntity.memberEntity)
			.on(QItemEntity.itemEntity.memberId.eq(QMemberEntity.memberEntity.memberId))
			.where(QItemEntity.itemEntity.id.eq(itemId))
			.fetchFirst();
	}

}
