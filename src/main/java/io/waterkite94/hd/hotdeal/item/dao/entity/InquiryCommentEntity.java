package io.waterkite94.hd.hotdeal.item.dao.entity;

import io.waterkite94.hd.hotdeal.common.wrapper.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "inquiry_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryCommentEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "name", columnDefinition = "bigint", nullable = false)
	private Long id;

	@Column(name = "comment", columnDefinition = "varchar(255)", nullable = false)
	private String comment;

	@Column(name = "member_id", columnDefinition = "varchar(60)", nullable = false)
	private String memberId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_inquiry_id", referencedColumnName = "id", columnDefinition = "bigint")
	private ItemInquiryEntity itemInquiry;

	@Builder
	private InquiryCommentEntity(Long id, String comment, String memberId, ItemInquiryEntity itemInquiry) {
		this.id = id;
		this.comment = comment;
		this.memberId = memberId;
		this.itemInquiry = itemInquiry;
	}
}
