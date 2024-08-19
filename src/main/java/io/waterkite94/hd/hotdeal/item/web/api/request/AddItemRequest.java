package io.waterkite94.hd.hotdeal.item.web.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.waterkite94.hd.hotdeal.item.domain.dto.AddItemServiceDto;
import io.waterkite94.hd.hotdeal.item.domain.vo.Cost;
import io.waterkite94.hd.hotdeal.item.domain.vo.ItemType;
import io.waterkite94.hd.hotdeal.item.domain.vo.PreOrderSchedule;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemRequest {

	@JsonProperty("name")
	@NotBlank(message = "이름은 빈칸을 허용하지 않습니다.")
	private String name;

	@JsonProperty("cost")
	@NotNull(message = "금액 정보는 필수입니다.")
	private CostRequest costRequest;

	@JsonProperty("introduction")
	@NotBlank(message = "상품 설명은 빈칸을 허용하지 않습니다.")
	private String introduction;

	@JsonProperty("type")
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
			.name(this.name)
			.cost(Cost.of(costRequest.getPrice(), costRequest.getDiscount()))
			.introduction(introduction)
			.type(ItemType.fromString(type.name()))
			.preOrderSchedule(
				PreOrderSchedule.of(preOrderSchedule.year, preOrderSchedule.month, preOrderSchedule.date,
					preOrderSchedule.hour, preOrderSchedule.minute))
			.categoryId(categoryId)
			.build();
	}

	@Getter
	public static class CostRequest {

		@JsonProperty("price")
		@NotNull(message = "가격은 필수 값입니다.")
		@Min(value = 0, message = "가격은 0이하가 될 수 없습니다.")
		private Integer price;

		@JsonProperty("discount")
		@NotNull(message = "할인 금액은 필수 값입니다.")
		@Min(value = 0, message = "할인 금액은 0이하가 될 수 없습니다.")
		private Integer discount;

		@Builder
		private CostRequest(Integer price, Integer discount) {
			this.price = price;
			this.discount = discount;
		}
	}

	@Getter
	public static class PreOrderScheduleRequest {

		@Min(value = 2020, message = "예약 스케줄 연도는 2020 미만이 될 수 없습니다.")
		@Max(value = 2100, message = "예약 스케줄 연도는 2100 초과가 될 수 없습니다.")
		private Integer year;

		@Min(value = 1, message = "예약 스케줄 월은 1 미만이 될 수 없습니다.")
		@Max(value = 12, message = "예약 스케줄 월은 12 초과가 될 수 없습니다.")
		private Integer month;

		@Min(value = 0, message = "예약 스케줄 일은 1 미만이 될 수 없습니다.")
		@Max(value = 31, message = "예약 스케줄 일은 31 초과가 될 수 없습니다.")
		private Integer date;

		@Min(value = 0, message = "예약 스케줄 시간은 0 미만이 될 수 없습니다.")
		@Max(value = 24, message = "예약 스케줄 시간은 24 초과가 될 수 없습니다.")
		private Integer hour;

		@Min(value = 0, message = "예약 스케줄 분은 0 미만이 될 수 없습니다.")
		@Max(value = 60, message = "예약 스케줄 분은 60 초과가 될 수 없습니다.")
		private Integer minute;

		@Builder
		private PreOrderScheduleRequest(Integer year, Integer month, Integer date, Integer hour, Integer minute) {
			this.year = year;
			this.month = month;
			this.date = date;
			this.hour = hour;
			this.minute = minute;
		}
	}
}
