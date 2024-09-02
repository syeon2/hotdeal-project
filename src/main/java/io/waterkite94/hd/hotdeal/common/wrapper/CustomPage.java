package io.waterkite94.hd.hotdeal.common.wrapper;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class CustomPage<T> {

	private final List<T> content;

	@JsonProperty("total_count")
	private final Long totalCount;

	private CustomPage(List<T> content, Long totalCount) {
		this.content = content;
		this.totalCount = totalCount;
	}

	public static <T> CustomPage<T> of(List<T> content, Long totalCount) {
		return new CustomPage<>(content, totalCount);
	}

	public static <D, R> CustomPage<R> convertDtoToResponse(Page<D> page, Function<D, R> converter) {
		List<R> list = page.stream()
			.map(converter)
			.toList();

		return new CustomPage<>(list, page.getTotalElements());
	}
}
