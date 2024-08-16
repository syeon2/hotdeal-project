package io.waterkite94.hd.hotdeal.member.web.api;

import static org.mockito.BDDMockito.*;
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
import io.waterkite94.hd.hotdeal.member.service.AuthenticationCodeEmailService;

@WebMvcTest(AuthenticationCodeController.class)
class AuthenticationCodeControllerTest extends ControllerTestSupport {

	@MockBean
	private AuthenticationCodeEmailService authenticationCodeEmailService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "이메일 인증코드를 전송하는 api를 호출합니다.")
	void sendAuthenticationCodeToEmailApi() throws Exception {
		// given
		String email = "test@example.com";

		doNothing().when(authenticationCodeEmailService).sendAuthenticationCodeToEmail(email);

		// when // then
		mockMvc.perform(
				post("/api/v1/members/emails-authentication/{email}", email)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").exists())
			.andExpect(jsonPath("$.data").isString())
			.andDo(document("member-send-authentication",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("email").description("인증번호를 전송할 이메일")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data").type(JsonFieldType.STRING).description("요청 성공 여부 확인")
				)
			));
	}
}
