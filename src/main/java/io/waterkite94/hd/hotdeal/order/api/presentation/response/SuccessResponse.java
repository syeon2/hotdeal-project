package io.waterkite94.hd.hotdeal.order.api.presentation.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuccessResponse {

	private String message;

	public SuccessResponse(String message) {
		this.message = message;
	}
}
