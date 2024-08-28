package io.waterkite94.hd.hotdeal.order.web.api.normal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.order.service.normal.OrderService;
import io.waterkite94.hd.hotdeal.order.web.api.request.OrderItemRequest;
import io.waterkite94.hd.hotdeal.order.web.api.request.OrderItemsRequest;
import io.waterkite94.hd.hotdeal.order.web.api.response.OrderItemsResponse;
import io.waterkite94.hd.hotdeal.order.web.api.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/normal")
	public ApiResponse<OrderItemsResponse> orderItems(@RequestBody @Valid OrderItemsRequest request) {

		String savedOrderUuid = orderService.addOrderWithOrderDetail(
			request.getMemberId(),
			request.getAddress().toServiceDto(),
			request.getOrderItems().stream()
				.map(OrderItemRequest::toServiceDto)
				.toList()
		);

		return ApiResponse.ok(new OrderItemsResponse(savedOrderUuid));
	}

	@PostMapping("/order/{orderId}/cancel")
	public ApiResponse<SuccessResponse> cancelOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToCancel(orderId);

		return ApiResponse.ok(new SuccessResponse("Cancel order successfully"));
	}

	@PostMapping("/order/{orderId}/in-delivery")
	public ApiResponse<SuccessResponse> startDeliveryOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToInDelivery(orderId);

		return ApiResponse.ok(new SuccessResponse("Cancel order successfully"));
	}

	@PostMapping("/order/{orderId}/in-return")
	public ApiResponse<SuccessResponse> inReturnOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToInReturn(orderId);

		return ApiResponse.ok(new SuccessResponse("Return order successfully"));
	}

}
