package io.waterkite94.hd.hotdeal.item.api.presentation.request.vo;

import java.time.LocalDateTime;

import io.waterkite94.hd.hotdeal.item.api.domain.vo.PreOrderSchedule;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PreOrderScheduleRequest {

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
	@Max(value = 23, message = "예약 스케줄 시간은 24 초과가 될 수 없습니다.")
	private Integer hour;

	@Min(value = 0, message = "예약 스케줄 분은 0 미만이 될 수 없습니다.")
	@Max(value = 59, message = "예약 스케줄 분은 60 초과가 될 수 없습니다.")
	private Integer minute;

	@Builder
	private PreOrderScheduleRequest(Integer year, Integer month, Integer date, Integer hour, Integer minute) {
		this.year = year;
		this.month = month;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
	}

	public PreOrderSchedule toVo() {
		return PreOrderSchedule.of(year, month, date, hour, minute);
	}

	public static PreOrderScheduleRequest fromLocalDateTime(LocalDateTime localDateTime) {
		return new PreOrderScheduleRequest(
			localDateTime.getYear(),
			localDateTime.getMonthValue(),
			localDateTime.getDayOfMonth(),
			localDateTime.getHour(),
			localDateTime.getMinute());
	}
}
