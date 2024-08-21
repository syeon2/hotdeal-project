package io.waterkite94.hd.hotdeal.common.converter;

import org.springframework.core.convert.converter.Converter;

import io.waterkite94.hd.hotdeal.item.web.api.request.ItemTypeRequest;

public class StringToItemTypeConverter implements Converter<String, ItemTypeRequest> {
	@Override
	public ItemTypeRequest convert(String source) {
		return ItemTypeRequest.valueOf(source.toUpperCase());
	}
}
