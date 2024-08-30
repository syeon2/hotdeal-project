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

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import io.waterkite94.hd.hotdeal.ControllerTestSupport;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveRegisteredItemDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemId;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.item.service.admin.ItemAdminService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.ChangeItemInfoRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.ItemTypeRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.CostRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.PreOrderScheduleRequest;

@WebMvcTest(ItemAdminController.class)
class ItemAdminControllerTest extends ControllerTestSupport {

	@MockBean
	private ItemAdminService itemAdminService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "상품을 추가하는 Api를 호출합니다.")
	void registerItem() throws Exception {
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
			.andDo(document("item-register",
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
					fieldWithPath("quantity").type(JsonFieldType.NUMBER).description("상품 수량"),
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
	@DisplayName(value = "상품을 비활성화합니다.")
	void deactivateItem() throws Exception {
		// given
		Long itemId = 1L;
		String memberId = "memberId";

		// when // then
		mockMvc.perform(
				put("/api/v1/admin/items/{itemId}/inactive", itemId)
					.with(csrf())
					.header("X-MEMBER-ID", memberId)
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").exists())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("item-deactivate",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestHeaders(
					headerWithName("X-MEMBER-ID").description("회원 아이디")
				),
				pathParameters(
					parameterWithName("itemId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("요청 성공 여부 메시지")
				)
			));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "관리자가 등록한 상품들을 조회하는 Api를 호출합니다.")
	void retrieveRegisteredItems() throws Exception {
		// given
		String memberId = "memberId";
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "createdAt"));

		RetrieveRegisteredItemDto retrieveRegisteredItemDto = createFindAdminItemDto();
		long totalCount = 1L;

		given(itemAdminService.findAdminItems(any(), any()))
			.willReturn(new PageImpl<>(List.of(retrieveRegisteredItemDto), pageRequest, totalCount));

		// when // then
		mockMvc.perform(
				get("/api/v1/admin/items")
					.with(csrf())
					.header("X-MEMBER-ID", memberId)
					.param("page", "0")
					.param("size", "10")
					.param("sort", "createdAt,desc")
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content").isArray())
			.andExpect(jsonPath("$.data.total_count").isNumber())
			.andExpect(jsonPath("$.data.content[0].item_id").isNumber())
			.andExpect(jsonPath("$.data.content[0].item_uuid").isString())
			.andExpect(jsonPath("$.data.content[0].name").isString())
			.andExpect(jsonPath("$.data.content[0].cost.price").isNumber())
			.andExpect(jsonPath("$.data.content[0].cost.discount").isNumber())
			.andExpect(jsonPath("$.data.content[0].item_type").isString())
			.andExpect(jsonPath("$.data.content[0].quantity").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.year").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.month").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.date").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.hour").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.minute").isNumber())
			.andExpect(jsonPath("$.data.content[0].created_at").isString())
			.andExpect(jsonPath("$.data.content[0].category_id").isNumber())
			.andExpect(jsonPath("$.data.content[0].category_name").isString())
			.andDo(document("item-admin-retrieve-items",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestHeaders(
					headerWithName("X-MEMBER-ID").description("회원 아이디")
				),
				queryParameters(
					parameterWithName("page").description("페이지 순서"),
					parameterWithName("size").description("페이지 컨텐츠 개수"),
					parameterWithName("sort").description("페이지 정렬 기준 [(name, createdAt),(asc, desc)]"),
					parameterWithName("_csrf").description("")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.total_count").type(JsonFieldType.NUMBER).description("총 데이터 개수"),
					fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("조회한 데이터 배열"),
					fieldWithPath("data.content[0].item_id").type(JsonFieldType.NUMBER).description("상품 아이디"),
					fieldWithPath("data.content[0].item_uuid").type(JsonFieldType.STRING).description("상품 UUID"),
					fieldWithPath("data.content[0].name").type(JsonFieldType.STRING).description("상품 이름"),
					fieldWithPath("data.content[0].cost.price").type(JsonFieldType.NUMBER).description("상품 가격"),
					fieldWithPath("data.content[0].cost.discount").type(JsonFieldType.NUMBER).description("상품 할인 금액"),
					fieldWithPath("data.content[0].item_type").type(JsonFieldType.STRING).description("상품 타입"),
					fieldWithPath("data.content[0].pre_order_schedule.year").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 연도"),
					fieldWithPath("data.content[0].pre_order_schedule.month").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 월"),
					fieldWithPath("data.content[0].pre_order_schedule.date").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 일"),
					fieldWithPath("data.content[0].pre_order_schedule.hour").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 시간"),
					fieldWithPath("data.content[0].pre_order_schedule.minute").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 분"),
					fieldWithPath("data.content[0].pre_order_schedule.minute").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 분"),
					fieldWithPath("data.content[0].quantity").type(JsonFieldType.NUMBER).description("상품 재고"),
					fieldWithPath("data.content[0].created_at").type(JsonFieldType.STRING).description("상품 등록일"),
					fieldWithPath("data.content[0].category_id").type(JsonFieldType.NUMBER).description("상품 카테고리 아이디"),
					fieldWithPath("data.content[0].category_name").type(JsonFieldType.STRING).description("상품 카테고리 이름")
				)
			));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "상품 정보를 수정하는 API를 호출합니다.")
	void editItemInfo() throws Exception {
		// given
		String memberId = "memberId";
		Long itemId = 1L;
		ChangeItemInfoRequest request = createChangeItemInfoRequest();

		// when // then
		mockMvc.perform(
				put("/api/v1/admin/items/{itemId}/info", itemId)
					.with(csrf())
					.header("X-MEMBER-ID", memberId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("item-admin-edit",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestHeaders(
					headerWithName("X-MEMBER-ID").description("회원 아이디")
				),
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("상품 이름"),
					fieldWithPath("introduction").type(JsonFieldType.STRING).description("상품 세부 소개"),
					fieldWithPath("cost.price").type(JsonFieldType.NUMBER).description("상품 가격"),
					fieldWithPath("cost.discount").type(JsonFieldType.NUMBER).description("상품 할인 금액"),
					fieldWithPath("pre_order_schedule.year").type(JsonFieldType.NUMBER).description("예약 주문 연도"),
					fieldWithPath("pre_order_schedule.month").type(JsonFieldType.NUMBER).description("예약 주문 월"),
					fieldWithPath("pre_order_schedule.date").type(JsonFieldType.NUMBER).description("예약 주문 일"),
					fieldWithPath("pre_order_schedule.hour").type(JsonFieldType.NUMBER).description("예약 주문 시간"),
					fieldWithPath("pre_order_schedule.minute").type(JsonFieldType.NUMBER).description("예약 주문 분"),
					fieldWithPath("category_id").type(JsonFieldType.NUMBER).description("카테고리 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").description("요청 성공 메시지")
				)
			));
	}

	private RetrieveRegisteredItemDto createFindAdminItemDto() {
		return RetrieveRegisteredItemDto.builder()
			.itemId(new ItemId(1L, "member-uuid"))
			.itemName("itemName")
			.cost(Cost.of(10000, 1000))
			.itemType(ItemType.PRE_ORDER)
			.preOrderSchedule(LocalDateTime.now())
			.quantity(10)
			.createdAt(LocalDateTime.now())
			.categoryId(1L)
			.categoryName("categoryName")
			.build();
	}

	private AddItemRequest createAddItemRequest() {
		return AddItemRequest.builder()
			.name("name")
			.costRequest(CostRequest.builder()
				.price(10000)
				.discount(1000)
				.build())
			.introduction("introduction")
			.quantity(10)
			.type(ItemTypeRequest.PRE_ORDER)
			.preOrderSchedule(PreOrderScheduleRequest.builder()
				.year(2024)
				.month(10)
				.date(15)
				.hour(20)
				.minute(25)
				.build())
			.categoryId(1L)
			.build();
	}

	private ChangeItemInfoRequest createChangeItemInfoRequest() {
		return ChangeItemInfoRequest.builder()
			.name("name")
			.introduction("introduction")
			.cost(CostRequest.builder()
				.price(10000)
				.discount(1000)
				.build())
			.preOrderSchedule(PreOrderScheduleRequest.builder()
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
