package io.waterkite94.hd.hotdeal.item.web.api.request;

import io.waterkite94.hd.hotdeal.item.domain.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCategoryRequest {

	@NotBlank(message = "카테고리 이름은 빈칸을 허용하지 않습니다.")
	private String name;

	@Builder
	private AddCategoryRequest(String name) {
		this.name = name;
	}

	public Category toServiceDto() {
		return Category.builder()
			.name(name)
			.build();
	}
}
