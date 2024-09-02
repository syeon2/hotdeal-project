package io.waterkite94.hd.hotdeal.order.web.api.admin;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import io.waterkite94.hd.hotdeal.ControllerTestSupport;
import io.waterkite94.hd.hotdeal.order.service.user.OrderService;

@WebMvcTest(OrderAdminController.class)
class OrderAdminControllerTest extends ControllerTestSupport {

	@MockBean
	private OrderService orderService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "주문 상품을 배송처리하는 API를 호출합니다.")
	void startDeliveryOrder() throws Exception {
		// given
		String orderUuid = "orderUuid";

		// when  // then
		mockMvc.perform(
				put("/api/v1/admin/orders/order/{orderId}/in-delivery", orderUuid)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("order-indelivery-item",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("orderId").description("주문 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("상품 배송처리 성공 여부 메시지")
				)));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "주문 상품을 배송완료처리하는 API를 호출합니다.")
	void completeDeliveryOrder() throws Exception {
		// given
		String orderUuid = "orderUuid";

		// when  // then
		mockMvc.perform(
				put("/api/v1/admin/orders/order/{orderId}/comp-delivery", orderUuid)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("order-compdelivery-item",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("orderId").description("주문 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("상품 배송완료처리 성공 여부 메시지")
				)));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "주문 상품을 반품 완료처리하는 API를 호출합니다.")
	void completeReturnOrder() throws Exception {
		// given
		String orderUuid = "orderUuid";

		// when  // then
		mockMvc.perform(
				put("/api/v1/admin/orders/order/{orderId}/comp-return", orderUuid)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("order-compreturn-item",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("orderId").description("주문 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("상품 반품완료처리 성공 여부 메시지")
				)));
	}

}
