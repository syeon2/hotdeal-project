package io.waterkite94.hd.hotdeal.item.web.api.request;

import io.waterkite94.hd.hotdeal.item.domain.ItemInquiry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemInquiryRequest {

	@NotBlank(message = "문의글은 빈칸을 허용하지 않습니다.")
	private String comment;

	@NotBlank(message = "회원 아이디는 빈칸을 허용하지 않습니다.")
	private String memberId;

	@NotNull(message = "상품 아이디는 필수 값입니다.")
	private Long itemId;

	@Builder
	private AddItemInquiryRequest(String comment, String memberId, Long itemId) {
		this.comment = comment;
		this.memberId = memberId;
		this.itemId = itemId;
	}

	public ItemInquiry toServiceDto() {
		return ItemInquiry.builder()
			.comment(comment)
			.memberId(memberId)
			.itemId(itemId)
			.build();
	}
}
