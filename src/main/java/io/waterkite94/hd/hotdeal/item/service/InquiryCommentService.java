package io.waterkite94.hd.hotdeal.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.item.dao.InquiryCommentMapper;
import io.waterkite94.hd.hotdeal.item.dao.InquiryCommentRepository;
import io.waterkite94.hd.hotdeal.item.dao.ItemInquiryRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.InquiryCommentEntity;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemInquiryEntity;
import io.waterkite94.hd.hotdeal.item.domain.InquiryComment;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryCommentService {

	private final InquiryCommentRepository inquiryCommentRepository;
	private final ItemInquiryRepository itemInquiryRepository;
	private final InquiryCommentMapper inquiryCommentMapper;

	@Transactional
	public Long addInquiryComment(InquiryComment inquiryComment) {
		ItemInquiryEntity itemInquiryProxyEntity
			= itemInquiryRepository.getReferenceById(inquiryComment.getItemInquiryId());

		InquiryCommentEntity savedInquiryComment
			= inquiryCommentRepository.save(inquiryCommentMapper.toEntity(inquiryComment, itemInquiryProxyEntity));

		return savedInquiryComment.getId();
	}

	@Transactional
	public void deleteInquiryComment(Long inquiryCommentId, String memberId) {
		InquiryCommentEntity findInquiryComment = inquiryCommentRepository.findById(inquiryCommentId)
			.orElseThrow(() -> new IllegalArgumentException("Inquiry Comment Not Found"));

		if (!findInquiryComment.getMemberId().equals(memberId)) {
			throw new IllegalArgumentException("Unauthorized access");
		}

		inquiryCommentRepository.deleteById(inquiryCommentId);
	}
}
