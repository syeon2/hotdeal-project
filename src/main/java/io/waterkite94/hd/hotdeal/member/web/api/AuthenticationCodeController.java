package io.waterkite94.hd.hotdeal.member.web.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.member.service.AuthenticationCodeEmailService;
import io.waterkite94.hd.hotdeal.util.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class AuthenticationCodeController {

	private final AuthenticationCodeEmailService authenticationCodeEmailService;

	@PostMapping("/emails-authentication/{email}")
	public ApiResponse<String> sendEmailApi(@PathVariable String email) {
		authenticationCodeEmailService.sendAuthenticationCodeToEmail(email);

		return ApiResponse.ok("success");
	}
}
