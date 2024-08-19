package io.waterkite94.hd.hotdeal.item.web.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.service.ItemInquiryService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemInquiryRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.DeleteItemInquiryRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemInquiryController {

	private final ItemInquiryService itemInquiryService;

	@PostMapping("/item-inquiry")
	public ApiResponse<Long> addItemInquiry(@RequestBody AddItemInquiryRequest request) {
		Long savedItemInquiryId = itemInquiryService.addItemInquiry(request.toDomain());

		return ApiResponse.ok(savedItemInquiryId);
	}

	@DeleteMapping("/item-inquiry")
	public ApiResponse<String> deleteItemInquiry(@RequestBody DeleteItemInquiryRequest request) {
		itemInquiryService.deleteItemInquiry(request.getItemInquiryId(), request.getMemberId());

		return ApiResponse.ok("success");
	}
}
