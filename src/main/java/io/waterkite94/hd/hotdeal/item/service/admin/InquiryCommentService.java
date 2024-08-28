package io.waterkite94.hd.hotdeal.item.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.waterkite94.hd.hotdeal.common.error.exception.UnauthorizedMemberException;
import io.waterkite94.hd.hotdeal.item.dao.InquiryCommentMapper;
import io.waterkite94.hd.hotdeal.item.dao.ItemInquiryRepository;
import io.waterkite94.hd.hotdeal.item.dao.entity.ItemInquiryEntity;
import io.waterkite94.hd.hotdeal.item.domain.dto.InquiryCommentDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryCommentService {

	private final ItemInquiryRepository itemInquiryRepository;
	private final InquiryCommentMapper inquiryCommentMapper;

	@Transactional
	public void addInquiryComment(String memberId, InquiryCommentDto inquiryCommentDto) {
		ItemInquiryEntity itemInquiryProxyEntity
			= itemInquiryRepository.getReferenceById(inquiryCommentDto.getItemInquiryId());

		if (!itemInquiryProxyEntity.getItem().getMemberId().equals(memberId)) {
			throw new UnauthorizedMemberException("Unauthorized member Id");
		}

		itemInquiryProxyEntity.addInquiryComment(
			inquiryCommentMapper.toEntity(inquiryCommentDto, itemInquiryProxyEntity)
		);
	}

	@Transactional
	public void deleteInquiryComment(String memberId, Long inquiryCommentId) {
		ItemInquiryEntity findItemInquiry = itemInquiryRepository.findById(inquiryCommentId)
			.orElseThrow(() -> new IllegalArgumentException("Inquiry Comment Not Found"));

		if (!findItemInquiry.getItem().getMemberId().equals(memberId)) {
			throw new UnauthorizedMemberException("Unauthorized member Id");
		}

		findItemInquiry.deleteInquiryComment();
	}

	@Transactional
	public InquiryCommentDto findInquiryComment(Long itemInquiryId) {
		ItemInquiryEntity findInquiryComment = itemInquiryRepository.findById(itemInquiryId)
			.orElseThrow(() -> new IllegalArgumentException("Inquiry Comment Not Found"));

		return inquiryCommentMapper.toDomain(findInquiryComment.getInquiryComment());
	}
}
