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
import io.waterkite94.hd.hotdeal.member.domain.MemberRole;
import io.waterkite94.hd.hotdeal.member.domain.dto.AccessMemberDto;
import io.waterkite94.hd.hotdeal.member.service.MemberAccessService;
import io.waterkite94.hd.hotdeal.member.service.MemberJoinService;
import io.waterkite94.hd.hotdeal.member.service.MemberUpdateService;
import io.waterkite94.hd.hotdeal.member.web.api.request.JoinMemberRequest;
import io.waterkite94.hd.hotdeal.member.web.api.request.UpdateEmailRequest;
import io.waterkite94.hd.hotdeal.member.web.api.request.UpdateMemberRequest;
import io.waterkite94.hd.hotdeal.member.web.api.request.UpdatePasswordRequest;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest extends ControllerTestSupport {

	@MockBean
	private MemberJoinService memberJoinService;

	@MockBean
	private MemberUpdateService memberUpdateService;

	@MockBean
	private MemberAccessService memberAccessService;

	@Test
	@WithMockUser(roles = "USER")
	@DisplayName(value = "회원 가입 API를 테스트합니다.")
	void joinMemberApi() throws Exception {
		// given
		JoinMemberRequest request = createRequest();
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
			.andDo(document("member-join",
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

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "회원 아이디를 통해 회원 정보를 조회합니다.")
	void accessMemberInfo() throws Exception {
		// given
		String memberId = "memberId";

		AccessMemberDto accessMemberDto = createAccessMemberDto(memberId);
		given(memberAccessService.accessMember(memberId))
			.willReturn(accessMemberDto);

		// when // then
		mockMvc.perform(
				get("/api/v1/members/{memberId}", memberId)
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data").exists())
			.andExpect(jsonPath("$.data.memberId").isString())
			.andExpect(jsonPath("$.data.email").isString())
			.andExpect(jsonPath("$.data.name").isString())
			.andExpect(jsonPath("$.data.phoneNumber").isString())
			.andExpect(jsonPath("$.data.role").isString())
			.andExpect(jsonPath("$.data.address.city").isString())
			.andExpect(jsonPath("$.data.address.state").isString())
			.andExpect(jsonPath("$.data.address.address").isString())
			.andExpect(jsonPath("$.data.address.zipcode").isString())
			.andDo(document("member-access",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("memberId").description("회원 아이디")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.memberId").type(JsonFieldType.STRING).description("회원 아이디"),
					fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
					fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("전화번호"),
					fieldWithPath("data.role").type(JsonFieldType.STRING).description("회원 등급"),
					fieldWithPath("data.address.city").type(JsonFieldType.STRING).description("주소 (도시)"),
					fieldWithPath("data.address.state").type(JsonFieldType.STRING).description("주소 (도)"),
					fieldWithPath("data.address.address").type(JsonFieldType.STRING).description("주소 (세부 주소)"),
					fieldWithPath("data.address.zipcode").type(JsonFieldType.STRING).description("우편 번호")
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

	private JoinMemberRequest createRequest() {
		return JoinMemberRequest.builder()
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

	private static AccessMemberDto createAccessMemberDto(String memberId) {
		return AccessMemberDto.builder()
			.memberId(memberId)
			.email("test@example.com")
			.name("name")
			.phoneNumber("00011122222")
			.role(MemberRole.USER_NORMAL)
			.city("city")
			.state("state")
			.address("address")
			.zipcode("123456")
			.build();
	}
}
