package io.waterkite94.hd.hotdeal.order.api.presentation.admin;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.order.api.application.user.OrderService;
import io.waterkite94.hd.hotdeal.order.api.presentation.response.SuccessResponse;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController {

	private final OrderService orderService;

	@PutMapping("/order/{orderId}/in-delivery")
	public ApiResponse<SuccessResponse> startDeliveryOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToInDelivery(orderId);

		return ApiResponse.ok(new SuccessResponse("Start Delivery Order successfully"));
	}

	@PutMapping("/order/{orderId}/comp-delivery")
	public ApiResponse<SuccessResponse> completeDeliveryOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToCompleteDelivery(orderId);

		return ApiResponse.ok(new SuccessResponse("Complete Delivery Order successfully"));
	}

	@PutMapping("/order/{orderId}/comp-return")
	public ApiResponse<SuccessResponse> completeReturnOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToCompleteReturn(orderId);

		return ApiResponse.ok(new SuccessResponse("Complete Return Order successfully"));
	}
}
