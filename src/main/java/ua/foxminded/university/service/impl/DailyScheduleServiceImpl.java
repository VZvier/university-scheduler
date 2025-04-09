package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.model.DailySchedule;
import ua.foxminded.university.repository.LectureRepository;
import ua.foxminded.university.service.DaiLyScheduleService;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class DailyScheduleServiceImpl implements DaiLyScheduleService {

	private final StudentServiceImpl sServ;

	private final LecturerServiceImpl lrServ;

	private final LectureRepository lRep;

	public DailyScheduleServiceImpl(StudentServiceImpl sServ, LecturerServiceImpl lrServ, LectureRepository lRep) {
		this.sServ = sServ;
		this.lrServ = lrServ;
		this.lRep = lRep;
	}

	@Override
	public List<DailySchedule> getDailyScheduleForStudent(int studentId) {
		List<Lecture> lectures = new LinkedList<>();
		List<DailySchedule> schedule = new LinkedList<>();
		Student student = sServ.getStudent(studentId).get(0);
		log.info("Start select daily timetable for student №{} from database.", studentId);

		for (int dayOfWeek = 1; dayOfWeek < 6; dayOfWeek++) {
			if (student.getGroup() != null) {
				lectures = lRep.findLecturesByGroupsAndDayNumber(student.getGroup(), dayOfWeek);
			} else {
				log.error("Students group is null!");
			}
			Collections.sort(lectures);
			schedule.add(new DailySchedule(DayOfWeek.of(dayOfWeek), lectures));
			lectures = new LinkedList<>();
		}

		return schedule;
	}

	@Override
	public List<DailySchedule> getDailyScheduleForLecturer(int lecturerId) {
		List<Lecture> lectures = new LinkedList<>();
		List<DailySchedule> timetableForAWeek = new LinkedList<>();
		Lecturer lecture = lrServ.getLecturer(lecturerId).get(0);
		log.info("Start select daily timetable for lecturer №{} from database.", lecturerId);

		for (int dayOfWeek = 1; dayOfWeek < 6; dayOfWeek++) {

			if (lecture.getCourse() != null) {
				lectures = lRep.findLecturesByCourseIdAndDayNumber(lecture.getCourse().getId(), dayOfWeek);
				Collections.sort(lectures);
			}

			timetableForAWeek.add(new DailySchedule(DayOfWeek.of(dayOfWeek), lectures));
			lectures = new LinkedList<>();
		}

		return timetableForAWeek;
	}

	@Override
	public List<DailySchedule> getCommonDailySchedule() {
		List<DailySchedule> timetableForAWeek = new LinkedList<>();
		log.info("Start select common daily timetable from database.");

		for (int dayOfWeek = 1; dayOfWeek <= 5; dayOfWeek++) {
			List<Lecture> dailyLectures = lRep.findLectureByDayNumber(dayOfWeek);
			Collections.sort(dailyLectures);
			timetableForAWeek.add(new DailySchedule(DayOfWeek.of(dayOfWeek), dailyLectures));
		}

		return timetableForAWeek;
	}

	@Override
	public List<Lecture> getLectures(List<DailySchedule> ds) {
		List<Lecture> lectures = new LinkedList<>();
		for (DailySchedule dailySchedule: ds){
			lectures.addAll(dailySchedule.getLectures());
		}
		Collections.sort(lectures);
		return lectures;
	}
}