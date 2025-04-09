package ua.foxminded.university.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodSchedule {

	private int calculatingPeriod;

	private int workingDays;

	private DayOfWeek startPeriod;

	private List<DailySchedule> scheduleForRequiredPeriod;

	public PeriodSchedule(int days, List<DailySchedule> daySchedule) {
		this.calculatingPeriod = days;
		DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
		this.startPeriod = dayOfWeek;
		this.workingDays = calculateWorkingDays(days, dayOfWeek);
		this.scheduleForRequiredPeriod = getPeriodSchedule(daySchedule);
	}

	private int calculateWorkingDays(int totalDays, DayOfWeek day) {
		int period = totalDays;
		int startDay = day.getValue();
		int perWeek = 7;
		int toEndOfFirstWeek = perWeek - (startDay - 1);
		if (period <= toEndOfFirstWeek) {
			if (period <= toEndOfFirstWeek - 2) {
				return period;
			} else {
				return checkPeriod(period, toEndOfFirstWeek);
			}
		}
		int firstWeek = (toEndOfFirstWeek > 2) ? (toEndOfFirstWeek - 2) : (0);
		int afterFirstWeek = period - toEndOfFirstWeek;
		int fullWeeks = afterFirstWeek / 7;
		int fullWeeksDays = fullWeeks * 7;
		int lastWeek = 0;
		if ((afterFirstWeek % 7) > 0) {
			lastWeek = ((afterFirstWeek % 7) <= 5) ? (afterFirstWeek % 7) : (afterFirstWeek % 7) - 1;
		}
		int forPeriodLessThanFirstWeek = 0;
		int weekEnds = fullWeeks * 2;
		return firstWeek + (fullWeeksDays - weekEnds) + lastWeek + forPeriodLessThanFirstWeek;
	}

	private int checkPeriod(int period, int toEndOfWeek) {
		if (period == toEndOfWeek) {
			return period - 2;
		} else {
			return period - 1;
		}
	}

	private List<DailySchedule> getPeriodSchedule(List<DailySchedule> daySchedule) {
		log.info("Size Of Daily Schedule == {}", daySchedule.size());
		List<DailySchedule> periodSchedule = new LinkedList<>();
		int period = this.workingDays;
		log.info("Working Days == {}", this.workingDays);
		int dayOfPeriod = 1;
		int startingDayNumber = (startPeriod.getValue() <= 5) ? startPeriod.getValue() : 1;

		while (dayOfPeriod <= period) {
			periodSchedule.add(daySchedule.get(startingDayNumber - 1));
			dayOfPeriod++;
			startingDayNumber += (startingDayNumber < 5) ? 1 : -4;
		}

		return periodSchedule;
	}
}
