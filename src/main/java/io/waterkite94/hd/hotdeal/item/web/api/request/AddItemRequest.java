package io.waterkite94.hd.hotdeal.item.web.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.domain.dto.AddItemServiceDto;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.CostRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.PreOrderScheduleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemRequest {

	@NotBlank(message = "이름은 빈칸을 허용하지 않습니다.")
	private String name;

	@JsonProperty("cost")
	@NotNull(message = "금액 정보는 필수입니다.")
	private CostRequest costRequest;

	@NotBlank(message = "상품 설명은 빈칸을 허용하지 않습니다.")
	private String introduction;

	@NotNull(message = "상품 타입은 none, pre_order, normal_order 중 한가지를 택해야합니다.")
	private ItemTypeRequest type;

	@JsonProperty("pre_order_schedule")
	private PreOrderScheduleRequest preOrderSchedule;

	@JsonProperty("category_id")
	@NotNull(message = "카테고리 아이디는 빈칸을 허용하지 않습니다.")
	private Long categoryId;

	@Builder
	private AddItemRequest(String name, CostRequest costRequest, String introduction, ItemTypeRequest type,
		PreOrderScheduleRequest preOrderSchedule, Long categoryId) {
		this.name = name;
		this.costRequest = costRequest;
		this.introduction = introduction;
		this.type = type;
		this.preOrderSchedule = preOrderSchedule;
		this.categoryId = categoryId;
	}

	public AddItemServiceDto toServiceDto() {
		return AddItemServiceDto.builder()
			.name(name)
			.cost(costRequest.toVo())
			.introduction(introduction)
			.type(type.toVo())
			.preOrderSchedule(preOrderSchedule.toVo())
			.categoryId(categoryId)
			.build();
	}
}
