package io.waterkite94.hd.hotdeal.item.web.api.user;

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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import io.waterkite94.hd.hotdeal.ControllerTestSupport;
import io.waterkite94.hd.hotdeal.item.domain.dto.InquiryCommentDto;
import io.waterkite94.hd.hotdeal.item.service.admin.InquiryCommentService;

@WebMvcTest(InquiryCommentController.class)
class InquiryCommentControllerTest extends ControllerTestSupport {

	@MockBean
	private InquiryCommentService inquiryCommentService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "문의 답글을 조회하는 API를 호출합니다.")
	void retrieveInquiryComment() throws Exception {
		// given
		Long itemInquiryId = 1L;
		InquiryCommentDto inquiryCommentDto = createInquiryCommentDto();

		given(inquiryCommentService.findInquiryComment(itemInquiryId))
			.willReturn(inquiryCommentDto);

		// when // then
		mockMvc.perform(
				get("/api/v1/inquiry-comment/{itemInquiryId}", itemInquiryId)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.comment").isString())
			.andExpect(jsonPath("$.data.created_at").isString())
			.andExpect(jsonPath("$.data.updated_at").isString())
			.andDo(document("inquiry-comment-retrieve",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("itemInquiryId").description("문의 글 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.comment").type(JsonFieldType.STRING).description("문의 답글 내용"),
					fieldWithPath("data.created_at").type(JsonFieldType.STRING).description("문의 답글 생성일"),
					fieldWithPath("data.updated_at").type(JsonFieldType.STRING).description("문의 답글 수정일")
				)));
	}

	private InquiryCommentDto createInquiryCommentDto() {
		return InquiryCommentDto.builder()
			.comment("comment")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}
}
