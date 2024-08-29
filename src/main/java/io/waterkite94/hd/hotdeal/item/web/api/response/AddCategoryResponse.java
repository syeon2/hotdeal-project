package io.waterkite94.hd.hotdeal.item.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class AddCategoryResponse {

	@JsonProperty("category_id")
	private Long categoryId;

	private AddCategoryResponse(Long categoryId) {
		this.categoryId = categoryId;
	}

	public static AddCategoryResponse of(Long categoryId) {
		return new AddCategoryResponse(categoryId);
	}
}
