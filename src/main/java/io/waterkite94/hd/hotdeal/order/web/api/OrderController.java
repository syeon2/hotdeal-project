package io.waterkite94.hd.hotdeal.order.web.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.order.service.normal.OrderService;
import io.waterkite94.hd.hotdeal.order.web.api.request.OrderItemRequest;
import io.waterkite94.hd.hotdeal.order.web.api.request.OrderItemsRequest;
import io.waterkite94.hd.hotdeal.order.web.api.response.OrderItemsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
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
}
