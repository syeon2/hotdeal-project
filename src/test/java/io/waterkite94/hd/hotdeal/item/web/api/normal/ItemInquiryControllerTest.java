package io.waterkite94.hd.hotdeal.item.web.api.normal;

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
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import io.waterkite94.hd.hotdeal.ControllerTestSupport;
import io.waterkite94.hd.hotdeal.item.domain.dto.RetrieveItemInquiriesDto;
import io.waterkite94.hd.hotdeal.item.service.normal.ItemInquiryService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemInquiryRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.DeleteItemInquiryRequest;

@WebMvcTest(controllers = ItemInquiryController.class)
class ItemInquiryControllerTest extends ControllerTestSupport {

	@MockBean
	private ItemInquiryService itemInquiryService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "문의 글을 추가하는 API를 호출합니다.")
	void addItemInquiry() throws Exception {
		// given
		Long itemInquiryId = 1L;

		AddItemInquiryRequest request = createAddItemInquiryRequest();
		given(itemInquiryService.addItemInquiry(any()))
			.willReturn(itemInquiryId);

		// when // then
		mockMvc.perform(
				post("/api/v1/item-inquiry")
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.item_inquiry_id").isNumber())
			.andDo(document("item-inquiry-add",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("comment").type(JsonFieldType.STRING).description("문의 글"),
					fieldWithPath("member_id").type(JsonFieldType.STRING).description("작성자 아이디"),
					fieldWithPath("item_id").type(JsonFieldType.NUMBER).description("문의 상품 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.item_inquiry_id").type(JsonFieldType.NUMBER).description("문의 글 아이디")
				)));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "문의 글을 삭제하는 API를 호출합니다.")
	void deleteItemInquiry() throws Exception {
		// given
		DeleteItemInquiryRequest request = createDeleteItemInquiryRequest();

		// when // then
		mockMvc.perform(
				delete("/api/v1/item-inquiry")
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("item-inquiry-delete",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("item_inquiry_id").type(JsonFieldType.NUMBER).description("문의 상품 아이디"),
					fieldWithPath("member_id").type(JsonFieldType.STRING).description("회원 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("요청 결과 메시지")
				)));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "문의 글 리스트를 조회하는 API를 호출합니다.")
	void findItemInquiriesApi() throws Exception {
		// given
		Long itemId = 1L;
		Long offset = 0L;

		RetrieveItemInquiriesDto retrieveItemInquiryDto = createRetrieveItemInquiryDto();
		given(itemInquiryService.findItemInquiries(itemId, offset))
			.willReturn(List.of(retrieveItemInquiryDto));

		// when // then
		mockMvc.perform(
				get("/api/v1/item-inquiry")
					.with(csrf())
					.param("item-id", itemId.toString())
					.param("offset", offset.toString())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].item_inquiry_id").isNumber())
			.andExpect(jsonPath("$.data[0].comment").isString())
			.andExpect(jsonPath("$.data[0].created_at").isString())
			.andExpect(jsonPath("$.data[0].updated_at").isString())
			.andExpect(jsonPath("$.data[0].member_name").isString())
			.andDo(document("item-inquiry-retrieve-all",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				queryParameters(
					parameterWithName("item-id").description("상품 아이디"),
					parameterWithName("offset").description("마지막 문의 글 아이디"),
					parameterWithName("_csrf").description("")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data[0].item_inquiry_id").type(JsonFieldType.NUMBER).description("문의 글 아이디"),
					fieldWithPath("data[0].comment").type(JsonFieldType.STRING).description("문의 글"),
					fieldWithPath("data[0].created_at").type(JsonFieldType.STRING).description("문의 글 생성 시간"),
					fieldWithPath("data[0].updated_at").type(JsonFieldType.STRING).description("문의 글 수정 시간"),
					fieldWithPath("data[0].member_name").type(JsonFieldType.STRING).description("문의 글 작성자 이름")
				)));
	}

	private static RetrieveItemInquiriesDto createRetrieveItemInquiryDto() {
		return RetrieveItemInquiriesDto.builder()
			.itemInquiryId(1L)
			.comment("comment")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.memberName("memberName")
			.build();
	}

	private DeleteItemInquiryRequest createDeleteItemInquiryRequest() {
		return DeleteItemInquiryRequest.builder()
			.itemInquiryId(1L)
			.memberId("memberId")
			.build();
	}

	private AddItemInquiryRequest createAddItemInquiryRequest() {
		return AddItemInquiryRequest.builder()
			.comment("comment")
			.memberId("memberId")
			.itemId(1L)
			.build();
	}
}
