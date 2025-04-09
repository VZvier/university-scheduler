package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.model.DailySchedule;
import ua.foxminded.university.model.PeriodSchedule;
import ua.foxminded.university.service.PeriodScheduleService;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class PeriodScheduleServiceImpl implements PeriodScheduleService {
	
	@Override
	public PeriodSchedule getPeriodSchedule(int period, List<DailySchedule> weeklyTimeTable) {
		log.info("Start get timetable for specified period = {} days.", period);
		return new PeriodSchedule(period, weeklyTimeTable);
	}

	@Override
	public List<Lecture> getLectures(PeriodSchedule ps) {
		List<DailySchedule> ds = ps.getScheduleForRequiredPeriod();
		List<Lecture> lectures = new LinkedList<>();
		for(DailySchedule schedule: ds) {
			lectures.addAll(schedule.getLectures());
		}
		return lectures;
	}
}