package io.waterkite94.hd.hotdeal.item.web.api.admin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.service.admin.InquiryCommentService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddInquiryCommentRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.DeleteInquiryCommentRequest;
import io.waterkite94.hd.hotdeal.item.web.api.response.ItemSuccessResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/inquiry-comment")
@RequiredArgsConstructor
public class InquiryCommentAdminController {

	private final InquiryCommentService inquiryCommentService;

	@PostMapping
	public ApiResponse<ItemSuccessResponse> addInquiryComment(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@RequestBody AddInquiryCommentRequest request
	) {
		inquiryCommentService.addInquiryComment(memberId, request.toServiceDto());

		return ApiResponse.ok(new ItemSuccessResponse("Save Inquiry Comment Successfully"));
	}

	@DeleteMapping
	public ApiResponse<ItemSuccessResponse> deleteInquiryComment(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@RequestBody DeleteInquiryCommentRequest request
	) {
		inquiryCommentService.deleteInquiryComment(memberId, request.getInquiryCommentId());

		return ApiResponse.ok(new ItemSuccessResponse("Delete Inquiry Comment Successfully"));
	}
}
