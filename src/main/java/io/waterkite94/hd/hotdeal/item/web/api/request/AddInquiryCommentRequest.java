package io.waterkite94.hd.hotdeal.item.web.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.domain.dto.InquiryCommentDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddInquiryCommentRequest {

	@JsonProperty("comment")
	@NotBlank(message = "문의 답글 내용은 빈칸을 허용하지 않습니다.")
	private String comment;

	@JsonProperty("item_inquiry_id")
	@NotNull(message = "문의 글 아이디는 필수 값입니다.")
	private Long itemInquiryId;

	@Builder
	private AddInquiryCommentRequest(String comment, Long itemInquiryId) {
		this.comment = comment;
		this.itemInquiryId = itemInquiryId;
	}

	public InquiryCommentDto toServiceDto() {
		return InquiryCommentDto.builder()
			.comment(comment)
			.itemInquiryId(itemInquiryId)
			.build();
	}
}
