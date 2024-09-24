package io.waterkite94.hd.hotdeal.item.api.presentation.response.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PreOrderScheduleResponse {

	private final Integer year;
	private final Integer month;
	private final Integer date;
	private final Integer hour;
	private final Integer minute;

	@Builder
	private PreOrderScheduleResponse(Integer year, Integer month, Integer date, Integer hour, Integer minute) {
		this.year = year;
		this.month = month;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
	}

	public static PreOrderScheduleResponse fromLocalDateTime(LocalDateTime localDateTime) {
		return new PreOrderScheduleResponse(
			localDateTime.getYear(),
			localDateTime.getMonthValue(),
			localDateTime.getDayOfMonth(),
			localDateTime.getHour(),
			localDateTime.getMinute());
	}

}
