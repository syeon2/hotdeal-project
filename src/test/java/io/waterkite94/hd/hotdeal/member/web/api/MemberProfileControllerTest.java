package io.waterkite94.hd.hotdeal.member.web.api;

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
import io.waterkite94.hd.hotdeal.member.service.MemberProfileService;
import io.waterkite94.hd.hotdeal.member.web.api.request.CreateMemberRequest;

@WebMvcTest(controllers = MemberProfileController.class)
class MemberProfileControllerTest extends ControllerTestSupport {

	@MockBean
	private MemberProfileService memberProfileService;

	@Test
	@WithMockUser(roles = "USER")
	@DisplayName(value = "회원 가입 API를 테스트합니다.")
	void createMemberApi() throws Exception {
		// given
		CreateMemberRequest request = createRequest();
		String memberId = "memberId";

		given(memberProfileService.createMember(any(), any(), any())).willReturn(memberId);

		// when // then
		mockMvc.perform(
				post("/api/v1/members")
					.with(csrf())
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").exists())
			.andExpect(jsonPath("$.data.memberId").isString())
			.andDo(document("member-create",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
					fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화번호"),
					fieldWithPath("city").type(JsonFieldType.STRING).description("도시"),
					fieldWithPath("state").type(JsonFieldType.STRING).description("도(경기도)"),
					fieldWithPath("address").type(JsonFieldType.STRING).description("세부 주소"),
					fieldWithPath("zipcode").type(JsonFieldType.STRING).description("우편 변호"),
					fieldWithPath("authenticationCode").type(JsonFieldType.STRING).description("이메일 인증 코드")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.memberId").type(JsonFieldType.STRING).description("회원 아이디")
				)
			));
	}

	private CreateMemberRequest createRequest() {
		return CreateMemberRequest.builder()
			.email("waterkite94@gmail.com")
			.password("12345678")
			.name("suyeon")
			.phoneNumber("00011112222")
			.city("city")
			.state("state")
			.address("address")
			.zipcode("123456")
			.authenticationCode("000111")
			.build();
	}
}
