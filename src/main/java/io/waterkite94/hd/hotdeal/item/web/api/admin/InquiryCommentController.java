package io.waterkite94.hd.hotdeal.item.web.api.admin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.domain.dto.InquiryCommentDto;
import io.waterkite94.hd.hotdeal.item.service.admin.InquiryCommentService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddInquiryCommentRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.DeleteInquiryCommentRequest;
import io.waterkite94.hd.hotdeal.item.web.api.response.AddInquiryCommentResponse;
import io.waterkite94.hd.hotdeal.item.web.api.response.ItemSuccessResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/inquiry-comment")
@RequiredArgsConstructor
public class InquiryCommentController {

	private final InquiryCommentService inquiryCommentService;

	@PostMapping
	public ApiResponse<AddInquiryCommentResponse> addInquiryComment(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@RequestBody AddInquiryCommentRequest request
	) {
		Long savedInquiryCommentId = inquiryCommentService.addInquiryComment(memberId, request.toServiceDto());

		return ApiResponse.ok(new AddInquiryCommentResponse(savedInquiryCommentId));
	}

	@DeleteMapping
	public ApiResponse<ItemSuccessResponse> deleteInquiryComment(
		@RequestHeader("X-MEMBER-ID") String memberId,
		@RequestBody DeleteInquiryCommentRequest request
	) {
		inquiryCommentService.deleteInquiryComment(memberId, request.getInquiryCommentId());

		return ApiResponse.ok(new ItemSuccessResponse("Delete Inquiry Comment Successfully"));
	}

	@GetMapping
	public ApiResponse<InquiryCommentDto> findInquiryComment(@RequestParam Long itemInquiryId) {
		InquiryCommentDto findInquiryCommentDto = inquiryCommentService.searchInquiryCommentByItemInquiryId(
			itemInquiryId);

		return ApiResponse.ok(findInquiryCommentDto);
	}
}
