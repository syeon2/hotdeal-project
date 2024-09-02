package io.waterkite94.hd.hotdeal.item.web.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.domain.dto.ChangeItemInfoDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.domain.vo.PreOrderSchedule;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.CostRequest;
import io.waterkite94.hd.hotdeal.item.web.api.request.vo.PreOrderScheduleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangeItemInfoRequest {

	@JsonProperty("name")
	@NotBlank(message = "이름은 빈칸을 허용하지 않습니다.")
	private String name;

	@JsonProperty("introduction")
	@NotBlank(message = "상품 소개는 빈칸을 허용하지 않습니다.")
	private String introduction;

	@JsonProperty("cost")
	private CostRequest cost;

	@JsonProperty("pre_order_schedule")
	private PreOrderScheduleRequest preOrderSchedule;

	@JsonProperty("category_id")
	@NotNull(message = "카테고리 아이디는 필수 값입니다.")
	private Long categoryId;

	@Builder
	private ChangeItemInfoRequest(String name, String introduction, CostRequest cost,
		PreOrderScheduleRequest preOrderSchedule, Long categoryId) {
		this.name = name;
		this.introduction = introduction;
		this.cost = cost;
		this.preOrderSchedule = preOrderSchedule;
		this.categoryId = categoryId;
	}

	public ChangeItemInfoDto toServiceDto() {
		return ChangeItemInfoDto.builder()
			.name(name)
			.introduction(introduction)
			.cost(Cost.of(cost.getPrice(), cost.getDiscount()))
			.preOrderSchedule(PreOrderSchedule.of(
				preOrderSchedule.getYear(),
				preOrderSchedule.getMonth(),
				preOrderSchedule.getDate(),
				preOrderSchedule.getHour(),
				preOrderSchedule.getMinute()
			))
			.categoryId(categoryId)
			.build();
	}
}
