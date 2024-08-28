package io.waterkite94.hd.hotdeal.order.web.api.admin;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.order.service.normal.OrderService;
import io.waterkite94.hd.hotdeal.order.web.api.response.SuccessResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController {

	private final OrderService orderService;

	@PostMapping("/order/{orderId}/comp-delivery")
	public ApiResponse<SuccessResponse> completeDeliveryOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToCompleteDelivery(orderId);

		return ApiResponse.ok(new SuccessResponse("Complete Delivery Order successfully"));
	}

	@PostMapping("/order/{orderId}/comp-return")
	public ApiResponse<SuccessResponse> completeReturnOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToCompleteReturn(orderId);

		return ApiResponse.ok(new SuccessResponse("Complete Return Order successfully"));
	}
}
