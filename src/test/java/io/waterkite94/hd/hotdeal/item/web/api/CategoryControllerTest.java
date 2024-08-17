package io.waterkite94.hd.hotdeal.item.web.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import io.waterkite94.hd.hotdeal.ControllerTestSupport;
import io.waterkite94.hd.hotdeal.item.domain.Category;
import io.waterkite94.hd.hotdeal.item.service.CategoryService;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest extends ControllerTestSupport {

	@MockBean
	private CategoryService categoryService;

	@Test
	@WithMockUser(value = "USER")
	@DisplayName(value = "카테고리를 조회하는 API를 호출합니다.")
	void findAllCategoriesApi() throws Exception {
		// given
		Category category = createCategory();
		given(categoryService.findAllCategories()).willReturn(List.of(category));

		// when // then
		mockMvc.perform(
				get("/api/v1/categories")
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").isNumber())
			.andExpect(jsonPath("$.message").isEmpty())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data[0].id").value(category.getId()))
			.andExpect(jsonPath("$.data[0].name").value(category.getName()))
			.andDo(document("category-find-all",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("status").type(JsonFieldType.NUMBER).description("요청 상태 코드"),
					fieldWithPath("message").type(JsonFieldType.NULL).description("요청 결과 메시지"),
					fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("카테고리 아이디"),
					fieldWithPath("data[].name").type(JsonFieldType.STRING).description("카테고리 이름")
				)
			));
	}

	private Category createCategory() {
		return Category.builder()
			.id(1L)
			.name("name")
			.build();
	}
}
