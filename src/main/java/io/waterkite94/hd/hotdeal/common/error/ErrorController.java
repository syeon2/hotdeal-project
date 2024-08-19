package io.waterkite94.hd.hotdeal.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;

@RestControllerAdvice
public class ErrorController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ApiResponse<Void> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
		return ApiResponse.error(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse<Void> handlerValidationRequest(MethodArgumentNotValidException exception) {
		return ApiResponse.error(HttpStatus.BAD_REQUEST,
			exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	public ApiResponse<Void> handlerRuntimeException(RuntimeException exception) {
		return ApiResponse.error(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
}
