package ua.foxminded.university.service;

import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.model.DailySchedule;
import ua.foxminded.university.model.PeriodSchedule;

import java.util.List;

public interface PeriodScheduleService {

	PeriodSchedule getPeriodSchedule(int period, List<DailySchedule> weeklyTimeTable);
	
	List<Lecture> getLectures(PeriodSchedule ps);
}
