package io.waterkite94.hd.hotdeal.item.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.UnauthorizedMemberException;
import io.waterkite94.hd.hotdeal.item.dao.InquiryCommentMapper;
import io.waterkite94.hd.hotdeal.item.dao.InquiryCommentRepository;
import io.waterkite94.hd.hotdeal.item.dao.ItemInquiryRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.InquiryCommentEntity;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemInquiryEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.InquiryCommentDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryCommentService {

	private final InquiryCommentRepository inquiryCommentRepository;
	private final ItemInquiryRepository itemInquiryRepository;
	private final InquiryCommentMapper inquiryCommentMapper;

	@Transactional
	public Long addInquiryComment(String memberId, InquiryCommentDto inquiryCommentDto) {
		ItemInquiryEntity itemInquiryProxyEntity
			= itemInquiryRepository.getReferenceById(inquiryCommentDto.getItemInquiryId());

		if (!itemInquiryProxyEntity.getItem().getMemberId().equals(memberId)) {
			throw new UnauthorizedMemberException("Unauthorized member Id");
		}

		InquiryCommentEntity savedInquiryComment
			= inquiryCommentRepository.save(inquiryCommentMapper.toEntity(inquiryCommentDto, itemInquiryProxyEntity));

		return savedInquiryComment.getId();
	}

	@Transactional
	public void deleteInquiryComment(String memberId, Long inquiryCommentId) {
		InquiryCommentEntity findInquiryComment = inquiryCommentRepository.findById(inquiryCommentId)
			.orElseThrow(() -> new IllegalArgumentException("Inquiry Comment Not Found"));

		if (!findInquiryComment.getItemInquiry().getItem().getMemberId().equals(memberId)) {
			throw new UnauthorizedMemberException("Unauthorized member Id");
		}

		inquiryCommentRepository.deleteById(inquiryCommentId);
	}

	@Transactional
	public InquiryCommentDto searchInquiryCommentByItemInquiryId(Long itemInquiryId) {
		// return inquiryCommentRepository.findInquiryCommentDto(itemInquiryId)
		// 	.orElseThrow(() -> new IllegalArgumentException("Inquiry Comment Not Found"));
		return null;
	}
}
