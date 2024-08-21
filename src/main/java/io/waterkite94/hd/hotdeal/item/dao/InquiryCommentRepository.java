package io.waterkite94.hd.hotdeal.item.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.waterkite94.hd.hotdeal.item.dao.entity.InquiryCommentEntity;

public interface InquiryCommentRepository extends JpaRepository<InquiryCommentEntity, Long> {

	Optional<InquiryCommentEntity> findByItemInquiryId(Long itemInquiryId);
}
