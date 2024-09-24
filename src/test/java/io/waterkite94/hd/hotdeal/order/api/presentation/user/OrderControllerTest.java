package io.waterkite94.hd.hotdeal.order.api.presentation.user;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import io.waterkite94.hd.hotdeal.ControllerTestSupport;
import io.waterkite94.hd.hotdeal.order.api.application.user.OrderService;
import io.waterkite94.hd.hotdeal.order.api.presentation.request.AddressRequest;
import io.waterkite94.hd.hotdeal.order.api.presentation.request.OrderItemRequest;
import io.waterkite94.hd.hotdeal.order.api.presentation.request.OrderItemsRequest;
import io.waterkite94.hd.hotdeal.order.api.presentation.request.PreOrderItemRequest;
import io.waterkite94.hd.hotdeal.order.api.presentation.request.PreOrderItemsRequest;

@WebMvcTest(OrderController.class)
class OrderControllerTest extends ControllerTestSupport {

	@MockBean
	private OrderService orderService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "일반 상품을 주문하는 API를 호출합니다.")
	void orderNormalItems() throws Exception {
		// given
		OrderItemsRequest request = createOrderItemRequest();

		given(orderService.addNormalOrderWithOrderDetail(any(), any(), any()))
			.willReturn("orderUuid");

		// when // then
		mockMvc.perform(
				post("/api/v1/orders/normal")
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data.order_uuid").isString())
			.andDo(document("order-add-normal-item",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("order_items").type(JsonFieldType.ARRAY).description("주문 상품 목록"),
					fieldWithPath("order_items[].item_id").type(JsonFieldType.NUMBER).description("주문 상품 아이디"),
					fieldWithPath("order_items[].quantity").type(JsonFieldType.NUMBER).description("주문 상품 수량"),
					fieldWithPath("address.city").type(JsonFieldType.STRING).description("주소 - 도시"),
					fieldWithPath("address.state").type(JsonFieldType.STRING).description("주소 - 도"),
					fieldWithPath("address.zipcode").type(JsonFieldType.STRING).description("주소 - 우편번호"),
					fieldWithPath("address.address").type(JsonFieldType.STRING).description("주소 - 세부 주소"),
					fieldWithPath("member_id").type(JsonFieldType.STRING).description("회원 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.order_uuid").type(JsonFieldType.STRING).description("주문 아이디")
				)));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "예약 상품을 주문하는 API를 호출합니다.")
	void orderPreOrderItems() throws Exception {
		// given
		PreOrderItemRequest request = createPreOrderItemRequest();

		given(orderService.addPreOrderItemWithOrderDetail(any(), any(), any()))
			.willReturn("orderUuid");

		// when  // then
		mockMvc.perform(
				post("/api/v1/orders/pre-order")
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data.order_uuid").isString())
			.andDo(document("order-add-preorder-item",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("order_items").type(JsonFieldType.OBJECT).description("주문 상품 목록"),
					fieldWithPath("order_items.item_id").type(JsonFieldType.NUMBER).description("주문 상품 아이디"),
					fieldWithPath("order_items.quantity").type(JsonFieldType.NUMBER).description("주문 상품 수량"),
					fieldWithPath("address.city").type(JsonFieldType.STRING).description("주소 - 도시"),
					fieldWithPath("address.state").type(JsonFieldType.STRING).description("주소 - 도"),
					fieldWithPath("address.zipcode").type(JsonFieldType.STRING).description("주소 - 우편번호"),
					fieldWithPath("address.address").type(JsonFieldType.STRING).description("주소 - 세부 주소"),
					fieldWithPath("member_id").type(JsonFieldType.STRING).description("회원 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.order_uuid").type(JsonFieldType.STRING).description("주문 아이디")
				)));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "주문 상품을 취소하는 API를 호출합니다.")
	void cancelOrder() throws Exception {
		// given
		String orderUuid = "orderUuid";

		// when  // then
		mockMvc.perform(
				put("/api/v1/orders/order/{orderId}/cancel", orderUuid)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("order-cancel-item",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("orderId").description("주문 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("주문 취소 성공 여부 메시지")
				)));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "주문 상품을 반품 요청하는 API를 호출합니다.")
	void inReturnOrder() throws Exception {
		// given
		String orderUuid = "orderUuid";

		// when  // then
		mockMvc.perform(
				put("/api/v1/orders/order/{orderId}/in-return", orderUuid)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("order-inreturn-item",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("orderId").description("주문 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("상품 반품처리 성공 여부 메시지")
				)));
	}

	private PreOrderItemRequest createPreOrderItemRequest() {
		return PreOrderItemRequest.builder()
			.orderItems(PreOrderItemsRequest.builder()
				.itemId(1L)
				.quantity(10)
				.build())
			.address(AddressRequest.builder()
				.city("city")
				.state("state")
				.address("address")
				.zipcode("12345")
				.build())
			.memberId("memberId")
			.build();
	}

	private OrderItemsRequest createOrderItemRequest() {
		return OrderItemsRequest.builder()
			.orderItems(List.of(OrderItemRequest.builder().itemId(1L).quantity(10).build()))
			.address(AddressRequest.builder()
				.city("city")
				.state("state")
				.address("address")
				.zipcode("12345")
				.build())
			.memberId("memberId")
			.build();
	}
}
