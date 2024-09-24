package io.waterkite94.hd.hotdeal.support.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;
}
