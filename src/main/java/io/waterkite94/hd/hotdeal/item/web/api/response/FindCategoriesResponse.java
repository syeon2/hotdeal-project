package io.waterkite94.hd.hotdeal.item.web.api.response;

import io.waterkite94.hd.hotdeal.item.domain.Category;
import lombok.Getter;

@Getter
public class FindCategoriesResponse {

	private final Long id;
	private final String name;

	private FindCategoriesResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static FindCategoriesResponse of(Category category) {
		return new FindCategoriesResponse(category.getId(), category.getName());
	}
}
