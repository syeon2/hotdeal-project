package io.waterkite94.hd.hotdeal.item.api.presentation.admin;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
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
import io.waterkite94.hd.hotdeal.item.api.application.admin.InquiryCommentService;
import io.waterkite94.hd.hotdeal.item.api.presentation.request.AddInquiryCommentRequest;
import io.waterkite94.hd.hotdeal.item.api.presentation.request.DeleteInquiryCommentRequest;

@WebMvcTest(InquiryCommentAdminController.class)
class InquiryCommentAdminControllerTest extends ControllerTestSupport {

	@MockBean
	private InquiryCommentService inquiryCommentService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "문의 답글을 추가합니다.")
	void addInquiryComment() throws Exception {
		// given
		String memberId = "memberId";
		AddInquiryCommentRequest request = createAddInquiryCommentRequest();

		// when // then
		mockMvc.perform(
				post("/api/v1/admin/inquiry-comment")
					.with(csrf())
					.header("X-MEMBER-ID", memberId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("inquiry-comment-add",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestHeaders(
					headerWithName("X-MEMBER-ID").description("상품 등록 회원 아이디")
				),
				requestFields(
					fieldWithPath("comment").type(JsonFieldType.STRING).description("답글 내용"),
					fieldWithPath("item_inquiry_id").type(JsonFieldType.NUMBER).description("문의 글 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("문의 결과 메시지")
				)));
		;
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "문의 답글을 삭제하는 API를 호출합니다.")
	void deleteInquiryComment() throws Exception {
		// given
		String memberId = "memberId";
		DeleteInquiryCommentRequest request = new DeleteInquiryCommentRequest(1L);

		// when // then
		mockMvc.perform(
				delete("/api/v1/admin/inquiry-comment")
					.with(csrf())
					.header("X-MEMBER-ID", memberId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.message").isString())
			.andDo(document("inquiry-comment-delete",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestHeaders(
					headerWithName("X-MEMBER-ID").description("상품 등록 회원 아이디")
				),
				requestFields(
					fieldWithPath("inquiry_comment_id").type(JsonFieldType.NUMBER).description("문의 답글 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.message").type(JsonFieldType.STRING).description("요청 결과 메시지")
				)));
		;
	}

	private AddInquiryCommentRequest createAddInquiryCommentRequest() {
		return AddInquiryCommentRequest.builder()
			.comment("comment")
			.itemInquiryId(1L)
			.build();
	}
}
