package io.waterkite94.hd.hotdeal.order.api.presentation.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.order.api.application.user.OrderService;
import io.waterkite94.hd.hotdeal.order.api.presentation.request.OrderItemRequest;
import io.waterkite94.hd.hotdeal.order.api.presentation.request.OrderItemsRequest;
import io.waterkite94.hd.hotdeal.order.api.presentation.request.PreOrderItemRequest;
import io.waterkite94.hd.hotdeal.order.api.presentation.response.OrderItemsResponse;
import io.waterkite94.hd.hotdeal.order.api.presentation.response.SuccessResponse;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/normal")
	public ApiResponse<OrderItemsResponse> orderNormalItems(@RequestBody @Valid OrderItemsRequest request) {

		String savedOrderUuid = orderService.addNormalOrderWithOrderDetail(
			request.getMemberId(),
			request.getAddress().toServiceDto(),
			request.getOrderItems().stream()
				.map(OrderItemRequest::toServiceDto)
				.toList()
		);

		return ApiResponse.ok(new OrderItemsResponse(savedOrderUuid));
	}

	@PostMapping("/pre-order")
	public ApiResponse<OrderItemsResponse> orderPreOrderItems(@RequestBody @Valid PreOrderItemRequest request) {

		String savedOrderUuid = orderService.addPreOrderItemWithOrderDetail(
			request.getMemberId(),
			request.getAddress().toServiceDto(),
			request.getOrderItems().toServiceDto()
		);

		return ApiResponse.ok(new OrderItemsResponse(savedOrderUuid));
	}

	@PutMapping("/order/{orderId}/cancel")
	public ApiResponse<SuccessResponse> cancelOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToCancel(orderId);

		return ApiResponse.ok(new SuccessResponse("Cancel Order successfully"));
	}

	@PutMapping("/order/{orderId}/in-return")
	public ApiResponse<SuccessResponse> inReturnOrder(@PathVariable("orderId") String orderId) {
		orderService.changeOrderStatusToInReturn(orderId);

		return ApiResponse.ok(new SuccessResponse("In Return Order successfully"));
	}

}
