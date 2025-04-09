package ua.foxminded.university.service;

import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.model.DailySchedule;

import java.util.List;

public interface DaiLyScheduleService {

	List<DailySchedule> getDailyScheduleForStudent(int studentId);

	List<DailySchedule> getDailyScheduleForLecturer(int lecturerId);

	List<DailySchedule> getCommonDailySchedule();

	List<Lecture> getLectures(List<DailySchedule> ds);
}
