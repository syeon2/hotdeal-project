package io.waterkite94.hd.hotdeal.support.wrapper;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private Integer status;
	private String message;
	private T data;

	@Builder
	private ApiResponse(Integer status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
		return ApiResponse.<T>builder()
			.status(status.value())
			.message(message)
			.data(data)
			.build();
	}

	public static <T> ApiResponse<T> ok(T data) {
		return of(HttpStatus.OK, null, data);
	}

	public static <T> ApiResponse<T> error(HttpStatus status, String message) {
		return of(status, message, null);
	}
}
