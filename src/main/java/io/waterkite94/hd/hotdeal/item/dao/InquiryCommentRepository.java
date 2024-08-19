package io.waterkite94.hd.hotdeal.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.item.dao.custom.InquiryCommentRepositoryCustom;
import io.waterkite94.hd.hotdeal.item.dao.entity.InquiryCommentEntity;

public interface InquiryCommentRepository extends JpaRepository<InquiryCommentEntity, Long>,
	InquiryCommentRepositoryCustom {
}
