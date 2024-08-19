package io.waterkite94.hd.hotdeal.item.web.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.domain.dto.InquiryCommentDto;
import io.waterkite94.hd.hotdeal.item.service.InquiryCommentService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddInquiryCommentRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.DeleteInquiryCommentRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/items/inquiry-comment")
@RequiredArgsConstructor
public class InquiryCommentController {

	private final InquiryCommentService inquiryCommentService;

	@PostMapping
	public ApiResponse<Long> addInquiryCommentApi(@RequestBody AddInquiryCommentRequest request) {
		Long savedInquiryCommentId = inquiryCommentService.addInquiryComment(request.toDomain());

		return ApiResponse.ok(savedInquiryCommentId);
	}

	@DeleteMapping
	public ApiResponse<String> deleteInquiryCommentApi(@RequestBody DeleteInquiryCommentRequest request) {
		inquiryCommentService.deleteInquiryComment(request.getInquiryCommentId(), request.getMemberId());

		return ApiResponse.ok("success");
	}

	@GetMapping
	public ApiResponse<InquiryCommentDto> findInquiryCommentApi(@RequestParam Long itemInquiryId) {
		InquiryCommentDto findInquiryCommentDto = inquiryCommentService.searchInquiryCommentByItemInquiryId(
			itemInquiryId);

		return ApiResponse.ok(findInquiryCommentDto);
	}
}
