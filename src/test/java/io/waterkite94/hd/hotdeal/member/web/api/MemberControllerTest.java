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
import io.waterkite94.hd.hotdeal.member.service.MemberJoinService;
import io.waterkite94.hd.hotdeal.member.service.MemberUpdateService;
import io.waterkite94.hd.hotdeal.member.web.api.request.CreateMemberRequest;
import io.waterkite94.hd.hotdeal.member.web.api.request.UpdateEmailRequest;
import io.waterkite94.hd.hotdeal.member.web.api.request.UpdateMemberRequest;
import io.waterkite94.hd.hotdeal.member.web.api.request.UpdatePasswordRequest;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest extends ControllerTestSupport {

	@MockBean
	private MemberJoinService memberJoinService;

	@MockBean
	private MemberUpdateService memberUpdateService;

	@Test
	@WithMockUser(roles = "USER")
	@DisplayName(value = "회원 가입 API를 테스트합니다.")
	void createMemberApi() throws Exception {
		// given
		CreateMemberRequest request = createRequest();
		String memberId = "memberId";

		given(memberJoinService.joinMember(any(), any(), any())).willReturn(memberId);

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
					fieldWithPath("verificationCode").type(JsonFieldType.STRING).description("이메일 인증 코드")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.memberId").type(JsonFieldType.STRING).description("회원 아이디")
				)
			));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "회원 정보를 변경하는 API를 호출합니다.")
	void updateMemberInfoApi() throws Exception {
		// given
		String memberId = "memberId";
		UpdateMemberRequest request = createUpdateMemberInfoRequest();

		// when // then
		mockMvc.perform(
				put("/api/v1/members/{memberId}/info", memberId)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isString())
			.andDo(document("member-update-info",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("memberId").description("회원 아이디")
				),
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("변경할 이름"),
					fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("변경할 전화번호"),
					fieldWithPath("city").type(JsonFieldType.STRING).description("변경할 도시"),
					fieldWithPath("state").type(JsonFieldType.STRING).description("변경할 도(지역)"),
					fieldWithPath("address").type(JsonFieldType.STRING).description("변경할 세부 주소"),
					fieldWithPath("zipcode").type(JsonFieldType.STRING).description("변경할 우편 번호")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data").type(JsonFieldType.STRING).description("요청 성공 여부 메시지")
				)
			));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "회원 이메일을 변경하는 API를 호출합니다.")
	void updateMemberEmailApi() throws Exception {
		// given
		String memberId = "memberId";
		UpdateEmailRequest request = new UpdateEmailRequest("test@example.com", "1234567890");

		// when // then
		mockMvc.perform(
				put("/api/v1/members/{memberId}/email", memberId)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isString())
			.andDo(document("member-update-email",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("memberId").description("회원 아이디")
				),
				requestFields(
					fieldWithPath("email").type(JsonFieldType.STRING).description("변경할 이메일"),
					fieldWithPath("verificationCode").type(JsonFieldType.STRING).description("이메일 인증 번호")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data").type(JsonFieldType.STRING).description("요청 성공 여부 메시지")
				)
			));
	}

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "회원 비밀번호를 변경하는 API를 호출합니다.")
	void updateMemberPasswordApi() throws Exception {
		// given
		String memberId = "memberId";
		UpdatePasswordRequest request = new UpdatePasswordRequest("currentPassword", "newPassword");

		// when // then
		mockMvc.perform(
				put("/api/v1/members/{memberId}/password", memberId)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isString())
			.andDo(document("member-update-password",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("memberId").description("회원 아이디")
				),
				requestFields(
					fieldWithPath("currentPassword").type(JsonFieldType.STRING).description("현재 비밀번호"),
					fieldWithPath("newPassword").type(JsonFieldType.STRING).description("변경할 비밀번호")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data").type(JsonFieldType.STRING).description("요청 성공 여부 메시지")
				)
			));
	}

	private UpdateMemberRequest createUpdateMemberInfoRequest() {
		return UpdateMemberRequest.builder()
			.name("changeName")
			.phoneNumber("11122223333")
			.city("newCity")
			.state("newState")
			.address("newAddress")
			.zipcode("123456")
			.build();
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
			.verificationCode("000111")
			.build();
	}
}
