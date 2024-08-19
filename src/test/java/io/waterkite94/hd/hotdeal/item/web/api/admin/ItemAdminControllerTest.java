package io.waterkite94.hd.hotdeal.item.web.api.admin;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
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
import io.waterkite94.hd.hotdeal.item.service.admin.ItemAdminService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.ItemTypeRequest;

@WebMvcTest(ItemAdminController.class)
class ItemAdminControllerTest extends ControllerTestSupport {

	@MockBean
	private ItemAdminService itemAdminService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "상품을 추가하는 Api를 호출합니다.")
	void addItemApi() throws Exception {
		// given
		String memberId = "memberId";
		AddItemRequest request = createAddItemRequest();

		given(itemAdminService.addItemWithMemberId(memberId, request.toServiceDto()))
			.willReturn(1L);

		// when // then
		mockMvc.perform(
				post("/api/v1/admin/items")
					.with(csrf())
					.header("X-MEMBER-ID", memberId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.item_id").isNumber())
			.andDo(document("item-add",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestHeaders(
					headerWithName("X-MEMBER-ID").description("회원 아이디")
				),
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("상품 이름"),
					fieldWithPath("cost.price").type(JsonFieldType.NUMBER).description("가격"),
					fieldWithPath("cost.discount").type(JsonFieldType.NUMBER).description("할인 금액"),
					fieldWithPath("introduction").type(JsonFieldType.STRING).description("상품 소개"),
					fieldWithPath("type").type(JsonFieldType.STRING).description("상품 타입"),
					fieldWithPath("pre_order_schedule.year").type(JsonFieldType.NUMBER).description("예약 주문 (연도)"),
					fieldWithPath("pre_order_schedule.month").type(JsonFieldType.NUMBER).description("예약 주문 (월)"),
					fieldWithPath("pre_order_schedule.date").type(JsonFieldType.NUMBER).description("예약 주문 (일)"),
					fieldWithPath("pre_order_schedule.hour").type(JsonFieldType.NUMBER).description("예약 주문 (시간)"),
					fieldWithPath("pre_order_schedule.minute").type(JsonFieldType.NUMBER).description("예약 주문 (분)"),
					fieldWithPath("category_id").type(JsonFieldType.NUMBER).description("카테고리 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.item_id").type(JsonFieldType.NUMBER).description("상품 아이디")
				)
			));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "상품을 삭제합니다.")
	void deleteItemApi() throws Exception {
		// given
		String memberId = "memberId";

		// when // then
		mockMvc.perform(
				delete("/admin/items/{memberId}", memberId)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isString())
			.andDo(document("item-delete",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("memberId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data").type(JsonFieldType.STRING).description("요청 성공 여부 메시지")
				)
			));
	}

	private AddItemRequest createAddItemRequest() {
		return AddItemRequest.builder()
			.name("name")
			.costRequest(AddItemRequest.CostRequest.builder()
				.price(10000)
				.discount(1000)
				.build())
			.introduction("introduction")
			.type(ItemTypeRequest.PRE_ORDER)
			.preOrderSchedule(AddItemRequest.PreOrderScheduleRequest.builder()
				.year(2024)
				.month(10)
				.date(15)
				.hour(20)
				.minute(25)
				.build())
			.categoryId(1L)
			.build();
	}
}
