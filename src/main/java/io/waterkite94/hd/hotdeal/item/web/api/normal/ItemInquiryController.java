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
import io.waterkite94.hd.hotdeal.item.domain.dto.ItemInquiryBoardDto;
import io.waterkite94.hd.hotdeal.item.service.normal.ItemInquiryService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemInquiryRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.DeleteItemInquiryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/item-inquiry")
@RequiredArgsConstructor
public class ItemInquiryController {

	private final ItemInquiryService itemInquiryService;

	@PostMapping
	public ApiResponse<Long> addItemInquiry(@RequestBody @Valid AddItemInquiryRequest request) {
		Long savedItemInquiryId = itemInquiryService.addItemInquiry(request.toServiceDto());

		return ApiResponse.ok(savedItemInquiryId);
	}

	@DeleteMapping
	public ApiResponse<String> deleteItemInquiry(@RequestBody DeleteItemInquiryRequest request) {
		itemInquiryService.deleteItemInquiry(request.getItemInquiryId(), request.getMemberId());

		return ApiResponse.ok("success");
	}

	@GetMapping
	public ApiResponse<List<ItemInquiryBoardDto>> findItemInquiriesApi(
		@RequestParam Long itemId,
		@RequestParam Long offset
	) {
		List<ItemInquiryBoardDto> findItemInquiries = itemInquiryService.searchItemInquiries(itemId, offset);

		return ApiResponse.ok(findItemInquiries);
	}
}
