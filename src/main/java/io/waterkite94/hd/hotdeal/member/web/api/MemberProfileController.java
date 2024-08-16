package io.waterkite94.hd.hotdeal.member.web.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.waterkite94.hd.hotdeal.common.wrapper.ApiResponse;
import io.waterkite94.hd.hotdeal.member.service.MemberProfileService;
import io.waterkite94.hd.hotdeal.member.web.api.request.CreateMemberRequest;
import io.waterkite94.hd.hotdeal.member.web.api.request.UpdateMemberRequest;
import io.waterkite94.hd.hotdeal.member.web.api.response.CreateMemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberProfileController {

	private final MemberProfileService memberProfileService;

	@PostMapping
	public ApiResponse<CreateMemberResponse> createMemberApi(@RequestBody @Valid CreateMemberRequest request) {
		String memberId = memberProfileService.createMember(
			request.toMemberDomain(),
			request.toAddressDomain(),
			request.getAuthenticationCode()
		);

		return ApiResponse.ok(new CreateMemberResponse(memberId));
	}

	@PutMapping("/{memberId}")
	public ApiResponse<String> updateMemberApi(@PathVariable String memberId,
		@RequestBody @Valid UpdateMemberRequest request) {
		memberProfileService.updateMemberInfo(memberId, request.toUpdateMemberDto(), request.toAddressDomain());

		return ApiResponse.ok("success");
	}
}
