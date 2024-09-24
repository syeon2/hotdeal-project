package io.waterkite94.hd.hotdeal.member.api.presentation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.member.api.application.AuthenticationCodeService;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class AuthenticationCodeController {

	private final AuthenticationCodeService authenticationCodeService;

	@PostMapping("/emails-authentication/{email}")
	public ApiResponse<String> sendEmailApi(@PathVariable String email) {
		authenticationCodeService.sendVerificationCodeToEmail(email);

		return ApiResponse.ok("success");
	}
}
