package io.waterkite94.hd.hotdeal.item.api.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteItemInquiryRequest {

	@JsonProperty("item_inquiry_id")
	@NotNull(message = "문의 글 아이디는 필수 값입니다.")
	private Long itemInquiryId;

	@JsonProperty("member_id")
	@NotBlank(message = "작성자 아이디는 빈칸을 허용하지 않습니다.")
	private String memberId;

	@Builder
	private DeleteItemInquiryRequest(Long itemInquiryId, String memberId) {
		this.itemInquiryId = itemInquiryId;
		this.memberId = memberId;
	}
}
