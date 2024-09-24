package io.waterkite94.hd.hotdeal.item.api.presentation.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.item.api.application.admin.InquiryCommentService;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.InquiryCommentDto;
import io.waterkite94.hd.hotdeal.item.api.presentation.response.RetrieveInquiryCommentResponse;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/inquiry-comment")
@RequiredArgsConstructor
public class InquiryCommentController {

	private final InquiryCommentService inquiryCommentService;

	@GetMapping("/{itemInquiryId}")
	public ApiResponse<RetrieveInquiryCommentResponse> retrieveInquiryComment(
		@PathVariable Long itemInquiryId
	) {
		InquiryCommentDto findInquiryCommentDto
			= inquiryCommentService.findInquiryComment(itemInquiryId);

		return ApiResponse.ok(RetrieveInquiryCommentResponse.of(findInquiryCommentDto));
	}
}
