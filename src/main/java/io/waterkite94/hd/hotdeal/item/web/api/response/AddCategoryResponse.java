package io.waterkite94.hd.hotdeal.item.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class AddCategoryResponse {

	@JsonProperty("category_id")
	private Long categoryId;

	public AddCategoryResponse(Long categoryId) {
		this.categoryId = categoryId;
	}
}
