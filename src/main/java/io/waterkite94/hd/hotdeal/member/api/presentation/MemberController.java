package io.waterkite94.hd.hotdeal.member.api.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.member.api.application.MemberAccessService;
import io.waterkite94.hd.hotdeal.member.api.application.MemberJoinService;
import io.waterkite94.hd.hotdeal.member.api.application.MemberUpdateService;
import io.waterkite94.hd.hotdeal.member.api.domain.dto.AccessMemberDto;
import io.waterkite94.hd.hotdeal.member.api.presentation.request.JoinMemberRequest;
import io.waterkite94.hd.hotdeal.member.api.presentation.request.UpdateEmailRequest;
import io.waterkite94.hd.hotdeal.member.api.presentation.request.UpdateMemberRequest;
import io.waterkite94.hd.hotdeal.member.api.presentation.request.UpdatePasswordRequest;
import io.waterkite94.hd.hotdeal.member.api.presentation.response.AccessMemberInfoResponse;
import io.waterkite94.hd.hotdeal.member.api.presentation.response.JoinMemberResponse;
import io.waterkite94.hd.hotdeal.support.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberJoinService memberJoinService;
	private final MemberUpdateService memberUpdateService;
	private final MemberAccessService memberAccessService;

	@PostMapping
	public ApiResponse<JoinMemberResponse> joinMemberApi(@RequestBody @Valid JoinMemberRequest request) {
		String memberId = memberJoinService.joinMember(
			request.toMemberDomain(),
			request.toAddressDomain(),
			request.getVerificationCode()
		);

		return ApiResponse.ok(new JoinMemberResponse(memberId));
	}

	@GetMapping("/{memberId}")
	private ApiResponse<AccessMemberInfoResponse> accessMemberInfoApi(@PathVariable String memberId) {
		AccessMemberDto findMember = memberAccessService.accessMember(memberId);

		return ApiResponse.ok(AccessMemberInfoResponse.of(findMember));
	}

	@PutMapping("/{memberId}/info")
	public ApiResponse<String> updateMemberApi(@PathVariable String memberId,
		@RequestBody @Valid UpdateMemberRequest request) {
		memberUpdateService.updateMemberInfo(memberId, request.toUpdateMemberDto(), request.toAddressDomain());

		return ApiResponse.ok("success");
	}

	@PutMapping("/{memberId}/email")
	public ApiResponse<String> updateMemberEmail(@PathVariable String memberId,
		@RequestBody @Valid UpdateEmailRequest request) {
		memberUpdateService.updateMemberEmail(memberId, request.getEmail(), request.getVerificationCode());

		return ApiResponse.ok("success");
	}

	@PutMapping("/{memberId}/password")
	public ApiResponse<String> updateMemberPassword(@PathVariable String memberId,
		@RequestBody @Valid UpdatePasswordRequest request) {
		memberUpdateService.updateMemberPassword(memberId, request.getCurrentPassword(), request.getNewPassword());

		return ApiResponse.ok("success");
	}
}
