package io.waterkite94.hd.hotdeal.member.web.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.member.service.AuthenticationCodeService;
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
