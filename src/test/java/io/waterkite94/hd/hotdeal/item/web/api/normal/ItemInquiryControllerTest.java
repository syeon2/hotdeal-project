package io.waterkite94.hd.hotdeal.item.web.api.normal;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
import io.waterkite94.hd.hotdeal.item.service.normal.ItemInquiryService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddItemInquiryRequest;

@WebMvcTest(controllers = ItemInquiryController.class)
class ItemInquiryControllerTest extends ControllerTestSupport {

	@MockBean
	private ItemInquiryService itemInquiryService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "문의 글을 추가하는 Api를 호출합니다.")
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

	private AddItemInquiryRequest createAddItemInquiryRequest() {
		return AddItemInquiryRequest.builder()
			.comment("comment")
			.memberId("memberId")
			.itemId(1L)
			.build();
	}
}
