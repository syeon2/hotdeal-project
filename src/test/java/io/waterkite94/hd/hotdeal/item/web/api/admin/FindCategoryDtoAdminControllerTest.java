package io.waterkite94.hd.hotdeal.item.web.api.admin;

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
import io.waterkite94.hd.hotdeal.item.service.admin.CategoryService;
import io.waterkite94.hd.hotdeal.item.web.api.request.AddCategoryRequest;

@WebMvcTest(CategoryAdminController.class)
class FindCategoryDtoAdminControllerTest extends ControllerTestSupport {

	@MockBean
	private CategoryService categoryService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "카테고리를 추가하는 API를 호출합니다.")
	void addCategory() throws Exception {
		// given
		AddCategoryRequest request = createRequest();
		given(categoryService.saveCategory(request.toServiceDto())).willReturn(1L);

		// when // then
		mockMvc.perform(
				post("/api/v1/admin/category")
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.category_id").isNumber())
			.andDo(document("category-add",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름")
				),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data.category_id").type(JsonFieldType.NUMBER).description("카테고리 아이디")
				)
			));
	}

	private AddCategoryRequest createRequest() {
		return AddCategoryRequest.builder()
			.name("name")
			.build();
	}
}
