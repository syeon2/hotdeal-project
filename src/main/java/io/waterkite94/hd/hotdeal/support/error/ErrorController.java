package io.waterkite94.hd.hotdeal.support.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.waterkite94.hd.hotdeal.support.error.exception.OutOfStockException;
import io.waterkite94.hd.hotdeal.support.error.exception.TooManyRequestException;
import io.waterkite94.hd.hotdeal.support.error.exception.UnauthorizedMemberException;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;

@RestControllerAdvice
public class ErrorController {

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(OutOfStockException.class)
	public ApiResponse<Void> handleOutOfStockException(OutOfStockException exception) {
		return ApiResponse.error(HttpStatus.CONFLICT, exception.getMessage());
	}

	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	@ExceptionHandler(TooManyRequestException.class)
	public ApiResponse<Void> handleTooManyRequestException(TooManyRequestException exception) {
		return ApiResponse.error(HttpStatus.TOO_MANY_REQUESTS, exception.getMessage());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedMemberException.class)
	public ApiResponse<Void> handleUnauthorizedMemberException(UnauthorizedMemberException exception) {
		return ApiResponse.error(HttpStatus.UNAUTHORIZED, exception.getMessage());
	}

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
