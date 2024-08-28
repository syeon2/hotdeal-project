package io.waterkite94.hd.hotdeal.item.dao.entity;

import io.waterkite94.hd.hotdeal.common.wrapper.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item_inquiry")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemInquiryEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", nullable = false)
	private Long id;

	@Column(name = "comment", columnDefinition = "varchar(255)", nullable = false)
	private String comment;

	@Column(name = "member_id", columnDefinition = "varchar(60)", nullable = false)
	private String memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", referencedColumnName = "id", columnDefinition = "bigint")
	private ItemEntity item;

	@OneToOne(
		mappedBy = "itemInquiry",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private InquiryCommentEntity inquiryComment;

	@Builder
	private ItemInquiryEntity(Long id, String comment, String memberId, ItemEntity item) {
		this.id = id;
		this.comment = comment;
		this.memberId = memberId;
		this.item = item;
	}

	public void addInquiryComment(InquiryCommentEntity inquiryComment) {
		this.inquiryComment = inquiryComment;
	}

	public void deleteInquiryComment() {
		this.inquiryComment = null;
	}
}
