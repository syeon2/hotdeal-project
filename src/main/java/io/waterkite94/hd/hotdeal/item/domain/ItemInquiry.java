package io.waterkite94.hd.hotdeal.item.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemInquiry {

	private Long id;

	private String comment;
	;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String memberId;

	private Long itemId;

	@Builder
	private ItemInquiry(Long id, String comment, LocalDateTime createdAt, LocalDateTime updatedAt, String memberId,
		Long itemId) {
		this.id = id;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.memberId = memberId;
		this.itemId = itemId;
	}
}
