package io.waterkite94.hd.hotdeal.item.web.api.response;

import java.util.List;

import io.waterkite94.hd.hotdeal.item.domain.dto.FindCategoryDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindCategoriesResponse {

	private final Long id;
	private final String name;

	public static FindCategoriesResponse of(FindCategoryDto findCategoryDto) {
		return new FindCategoriesResponse(findCategoryDto.getId(), findCategoryDto.getName());
	}

	public static List<FindCategoriesResponse> listOf(List<FindCategoryDto> categories) {
		return categories.stream()
			.map(FindCategoriesResponse::of)
			.toList();
	}
}
