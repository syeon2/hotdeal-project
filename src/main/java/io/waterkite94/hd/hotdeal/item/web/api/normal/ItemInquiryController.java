package io.waterkite94.hd.hotdeal.item.web.api.normal;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.item.service.normal.ItemInquiryService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemInquiryRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.DeleteItemInquiryRequest;
import io.waterkite94.hd.hotdeal.item.web.api.response.AddItemInquiryResponse;
import io.waterkite94.hd.hotdeal.item.web.api.response.ItemSuccessResponse;
import io.waterkite94.hd.hotdeal.item.web.api.response.RetrieveItemInquiriesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/item-inquiry")
@RequiredArgsConstructor
public class ItemInquiryController {

	private final ItemInquiryService itemInquiryService;

	@PostMapping
	public ApiResponse<AddItemInquiryResponse> addItemInquiry(@RequestBody @Valid AddItemInquiryRequest request) {
		Long savedItemInquiryId = itemInquiryService.addItemInquiry(request.toServiceDto());

		return ApiResponse.ok(new AddItemInquiryResponse(savedItemInquiryId));
	}

	@DeleteMapping
	public ApiResponse<ItemSuccessResponse> deleteItemInquiry(@RequestBody @Valid DeleteItemInquiryRequest request) {
		itemInquiryService.deleteItemInquiry(request.getItemInquiryId(), request.getMemberId());

		return ApiResponse.ok(new ItemSuccessResponse("Delete Item Inquiry successfully"));
	}

	@GetMapping
	public ApiResponse<List<RetrieveItemInquiriesResponse>> findItemInquiriesApi(
		@RequestParam(value = "item-id") Long itemId,
		@RequestParam Long offset
	) {
		List<RetrieveItemInquiriesResponse> findItemInquiries = itemInquiryService.findItemInquiries(itemId, offset)
			.stream()
			.map(RetrieveItemInquiriesResponse::of)
			.toList();

		return ApiResponse.ok(findItemInquiries);
	}
}
