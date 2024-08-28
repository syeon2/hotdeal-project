package io.waterkite94.hd.hotdeal.order.web.api.response;

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
