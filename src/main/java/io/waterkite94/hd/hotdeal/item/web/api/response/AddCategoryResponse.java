package io.waterkite94.hd.hotdeal.item.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddCategoryResponse {

	@JsonProperty("category_id")
	private final Long categoryId;
}
