package io.waterkite94.hd.hotdeal.member.dao.persistence.custom;

import static io.waterkite94.hd.hotdeal.member.dao.persistence.entity.QMemberEntity.*;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import io.waterkite94.hd.hotdeal.member.domain.dto.UpdateMemberDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Transactional
	@Modifying(clearAutomatically = true)
	@Override
	public void updateMemberInfo(String memberId, UpdateMemberDto updateMemberDto) {
		JPAUpdateClause update = queryFactory.update(memberEntity);

		setNotNullFields(update, memberEntity.email, updateMemberDto.getEmail());
		setNotNullFields(update, memberEntity.name, updateMemberDto.getName());
		setNotNullFields(update, memberEntity.phoneNumber, updateMemberDto.getPhoneNumber());

		update.where(memberEntity.memberId.eq(memberId))
			.execute();
	}

	private void setNotNullFields(JPAUpdateClause update, StringPath field, String value) {
		if (value != null) {
			update.set(field, value);
		}
	}
}
