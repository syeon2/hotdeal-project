package io.waterkite94.hd.hotdeal.item.dao.custom;

import java.util.Optional;

import io.waterkite94.hd.hotdeal.item.domain.dto.InquiryCommentDto;

public interface InquiryCommentRepositoryCustom {

	Optional<InquiryCommentDto> findInquiryCommentDto(Long itemInquiryId);
}
