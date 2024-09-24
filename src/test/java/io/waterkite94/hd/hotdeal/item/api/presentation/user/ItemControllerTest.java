package io.waterkite94.hd.hotdeal.item.api.presentation.user;

import static org.mockito.BDDMockito.*;
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
import io.waterkite94.hd.hotdeal.item.api.application.user.ItemService;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.ItemDetailDto;
import io.waterkite94.hd.hotdeal.item.api.domain.dto.RetrieveItemsDto;
import io.waterkite94.hd.hotdeal.item.api.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.api.presentation.request.ItemTypeRequest;

@WebMvcTest(ItemController.class)
class ItemControllerTest extends ControllerTestSupport {

	@MockBean
	private ItemService itemService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "상품 필터링을 통해 상품 리스트를 조회하는 API를 호출합니다.")
	void retrieveItems() throws Exception {
		// given
		Long categoryId = 1L;
		ItemTypeRequest typeRequest = ItemTypeRequest.PRE_ORDER;
		String search = "hello";
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("createdAt"));

		PageImpl<RetrieveItemsDto> pageRetrieveWithPage =
			new PageImpl<>(List.of(createRetrieveItemDto()), pageRequest, 1);
		given(itemService.findItems(any(), any(), any(), any()))
			.willReturn(pageRetrieveWithPage);

		// when // then
		mockMvc.perform(
				get("/api/v1/items")
					.with(csrf())
					.param("category-id", "1")
					.param("type", typeRequest.toString())
					.param("page", "0")
					.param("size", "10")
					.param("sort", "createdAt,desc")
					.param("search", search)
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content").isArray())
			.andExpect(jsonPath("$.data.content[0].item_id").isNumber())
			.andExpect(jsonPath("$.data.content[0].item_name").isString())
			.andExpect(jsonPath("$.data.content[0].cost.price").isNumber())
			.andExpect(jsonPath("$.data.content[0].cost.discount").isNumber())
			.andExpect(jsonPath("$.data.content[0].is_pre_order_item").isBoolean())
			.andExpect(jsonPath("$.data.content[0].quantity").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.year").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.month").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.date").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.hour").isNumber())
			.andExpect(jsonPath("$.data.content[0].pre_order_schedule.minute").isNumber())
			.andExpect(jsonPath("$.data.content[0].seller_id").isString())
			.andExpect(jsonPath("$.data.content[0].seller_name").isString())
			.andExpect(jsonPath("$.data.total_count").isNumber())
			.andDo(document("item-retrieve",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				queryParameters(
					parameterWithName("category-id").description("카테고리 아이디"),
					parameterWithName("type").description("상품 타입 (none, pre_order, normal_order)"),
					parameterWithName("page").description("페이지 순서"),
					parameterWithName("size").description("페이지 컨텐츠 개수"),
					parameterWithName("sort").description("페이지 정렬 기준 [(name, createdAt),(asc, desc)]"),
					parameterWithName("search").description("상품 이름 검색(Optional)"),
					parameterWithName("_csrf").description("")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.content[0].item_id").type(JsonFieldType.NUMBER).description("상품 아이디"),
					fieldWithPath("data.content[0].item_name").type(JsonFieldType.STRING).description("상품 이름"),
					fieldWithPath("data.content[0].cost.price").type(JsonFieldType.NUMBER).description("상품 가격"),
					fieldWithPath("data.content[0].cost.discount").type(JsonFieldType.NUMBER).description("상품 할인 금액"),
					fieldWithPath("data.content[0].quantity").type(JsonFieldType.NUMBER).description("상품 재고"),
					fieldWithPath("data.content[0].is_pre_order_item").type(JsonFieldType.BOOLEAN)
						.description("예약 주문 상품 확인"),
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
					fieldWithPath("data.content[0].seller_id").type(JsonFieldType.STRING).description("판매자 아이디"),
					fieldWithPath("data.content[0].seller_name").type(JsonFieldType.STRING).description("판매자 이름"),
					fieldWithPath("data.total_count").type(JsonFieldType.NUMBER).description("상품 총 개수")
				)));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "상품 세부 정보를 조회하는 API를 호출합니다.")
	void retrieveItemDetail() throws Exception {
		// given
		Long itemId = 1L;
		ItemDetailDto itemDetailDto = createItemDetailDto(itemId);

		given(itemService.findItemDetail(itemId)).willReturn(itemDetailDto);

		// when // then
		mockMvc.perform(
				get("/api/v1/items/{itemId}", itemId)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.item_id").isNumber())
			.andExpect(jsonPath("$.data.item_name").isString())
			.andExpect(jsonPath("$.data.cost.price").isNumber())
			.andExpect(jsonPath("$.data.cost.discount").isNumber())
			.andExpect(jsonPath("$.data.is_pre_order_item").isBoolean())
			.andExpect(jsonPath("$.data.pre_order_schedule.year").isNumber())
			.andExpect(jsonPath("$.data.pre_order_schedule.month").isNumber())
			.andExpect(jsonPath("$.data.pre_order_schedule.date").isNumber())
			.andExpect(jsonPath("$.data.pre_order_schedule.hour").isNumber())
			.andExpect(jsonPath("$.data.pre_order_schedule.minute").isNumber())
			.andExpect(jsonPath("$.data.created_at").isString())
			.andExpect(jsonPath("$.data.seller_id").isString())
			.andExpect(jsonPath("$.data.seller_name").isString())
			.andDo(document("item-retrieve-detail",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("itemId").description("상품 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.item_id").type(JsonFieldType.NUMBER).description("상품 아이디"),
					fieldWithPath("data.item_name").type(JsonFieldType.STRING).description("상품 이름"),
					fieldWithPath("data.cost.price").type(JsonFieldType.NUMBER).description("상품 가격"),
					fieldWithPath("data.cost.discount").type(JsonFieldType.NUMBER).description("상품 할인 금액"),
					fieldWithPath("data.introduction").type(JsonFieldType.STRING).description("상품 세부 소개"),
					fieldWithPath("data.is_pre_order_item").type(JsonFieldType.BOOLEAN).description("예약 주문 상품 확인"),
					fieldWithPath("data.pre_order_schedule.year").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 연도"),
					fieldWithPath("data.pre_order_schedule.month").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 월"),
					fieldWithPath("data.pre_order_schedule.date").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 일"),
					fieldWithPath("data.pre_order_schedule.hour").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 시간"),
					fieldWithPath("data.pre_order_schedule.minute").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 분"),
					fieldWithPath("data.pre_order_schedule.minute").type(JsonFieldType.NUMBER)
						.description("상품 예약 구매 분"),
					fieldWithPath("data.created_at").type(JsonFieldType.STRING).description("상품 등록일"),
					fieldWithPath("data.seller_id").type(JsonFieldType.STRING).description("판매자 아이디"),
					fieldWithPath("data.seller_name").type(JsonFieldType.STRING).description("판매자 이름")
				)));
	}

	private ItemDetailDto createItemDetailDto(Long itemId) {
		return ItemDetailDto.builder()
			.itemId(itemId)
			.itemName("itemName")
			.cost(Cost.of(10000, 1000))
			.introduction("introduction")
			.isPreOrderItem(true)
			.preOrderTime(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.sellerId("memberId")
			.sellerName("memberName")
			.build();
	}

	private static RetrieveItemsDto createRetrieveItemDto() {
		return RetrieveItemsDto.builder()
			.itemId(1L)
			.itemName("hello")
			.cost(Cost.of(10000, 1000))
			.isPreOrderItem(true)
			.quantity(10)
			.preOrderTime(LocalDateTime.now())
			.sellerId("sellerId")
			.sellerName("sellerName")
			.build();
	}
}
