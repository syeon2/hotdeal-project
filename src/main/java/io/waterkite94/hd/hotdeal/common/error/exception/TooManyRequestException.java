package io.waterkite94.hd.hotdeal.common.error.exception;

public class TooManyRequestException extends RuntimeException {
	public TooManyRequestException(String message) {
		super(message);
	}
}
