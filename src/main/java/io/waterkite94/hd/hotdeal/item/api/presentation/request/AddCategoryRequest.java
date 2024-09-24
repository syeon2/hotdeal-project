package io.waterkite94.hd.hotdeal.item.api.presentation.request;

import io.waterkite94.hd.hotdeal.item.api.domain.dto.AddCategoryDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCategoryRequest {

	@NotBlank(message = "카테고리 이름은 빈칸을 허용하지 않습니다.")
	private String name;

	public AddCategoryRequest(String name) {
		this.name = name;
	}

	public AddCategoryDto toServiceDto() {
		return new AddCategoryDto(name);
	}
}
