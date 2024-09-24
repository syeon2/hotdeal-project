package io.waterkite94.hd.hotdeal.support.error.exception;

public class TooManyRequestException extends RuntimeException {
	public TooManyRequestException(String message) {
		super(message);
	}
}
