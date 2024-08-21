package io.waterkite94.hd.hotdeal.item.domain.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PreOrderSchedule {

	private Integer year;
	private Integer month;
	private Integer date;
	private Integer hour;
	private Integer minute;

	@Builder
	private PreOrderSchedule(Integer year, Integer month, Integer date, Integer hour, Integer minute) {
		this.year = year;
		this.month = month;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
	}

	public static PreOrderSchedule of(Integer year, Integer month, Integer date, Integer hour, Integer minute) {
		return PreOrderSchedule.builder()
			.year(year)
			.month(month)
			.date(date)
			.hour(hour)
			.minute(minute)
			.build();
	}

	public boolean isBetweenStartTimeAndEndTime(LocalDateTime startTime, LocalDate endTime) {
		LocalDateTime inputTime = LocalDateTime.of(year, month, date, hour, minute);
		
		return startTime.isBefore(inputTime) && endTime.isAfter(inputTime.toLocalDate());
	}

	public LocalDateTime toLocalDateTime() {
		return LocalDateTime.of(year, month, date, hour, minute);
	}
}
