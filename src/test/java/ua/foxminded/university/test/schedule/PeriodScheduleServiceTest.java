package ua.foxminded.university.test.schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.model.DailySchedule;
import ua.foxminded.university.service.data.ObjectListToStringConverter;
import ua.foxminded.university.service.impl.PeriodScheduleServiceImpl;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PeriodScheduleServiceTest {

	private PeriodScheduleServiceImpl periodScheduleService = new PeriodScheduleServiceImpl();

	private ObjectListToStringConverter converter = new ObjectListToStringConverter();

	@Test
	void testForPeriodMoreThanWeek() {
		String actualResult = converter.convertShedule(
				periodScheduleService
						.getPeriodSchedule(15, getDailyScheduleList()).getScheduleForRequiredPeriod());

		assertThat(actualResult).isNotNull().isNotBlank();
	}

	@Test
	void testForPeriodLessThanWeek() {
		String actualResult = converter.convertShedule(
				periodScheduleService
						.getPeriodSchedule(3, getDailyScheduleList()).getScheduleForRequiredPeriod());

		assertThat(actualResult).isNotNull().isNotBlank();
	}

//~~~~~~~~~~~~~~~~~The methods containing data for creation instance of PeriodSchedule class~~~~~~~~~~~~~~~~
	private List<DailySchedule> getDailyScheduleList() {
		List<DailySchedule> ds = new LinkedList<>();
		ds.add(new DailySchedule(DayOfWeek.MONDAY, getMondayLectures()));
		ds.add(new DailySchedule(DayOfWeek.TUESDAY, getTuesdayLectures()));
		ds.add(new DailySchedule(DayOfWeek.WEDNESDAY, getWednsDayLectures()));
		ds.add(new DailySchedule(DayOfWeek.THURSDAY, getThursdayLectures()));
		ds.add(new DailySchedule(DayOfWeek.FRIDAY, getFridayLectures()));
		return ds;
	}

	private List<Lecture> getMondayLectures() {
		List<Lecture> l = new LinkedList<>();
		l.add(new Lecture(1, 1, 4, new Course(1, "test", "descr test"), getListGrop()));
		l.add(new Lecture(6, 1, 4, new Course(2, "test1", "descr test"), getListGrop()));
		return l;
	}

	private List<Lecture> getTuesdayLectures() {
		List<Lecture> l = new LinkedList<>();
		l.add(new Lecture(2, 2, 1, new Course(1, "test", "descr test"), getListGrop()));
		l.add(new Lecture(7, 2, 1, new Course(2, "test1", "descr test"), getListGrop()));
		return l;
	}

	private List<Lecture> getWednsDayLectures() {
		List<Lecture> l = new LinkedList<>();
		l.add(new Lecture(3, 3, 2, new Course(1, "test", "descr test"), getListGrop()));
		l.add(new Lecture(8, 3, 2, new Course(2, "test1", "descr test"), getListGrop()));
		return l;
	}

	private List<Lecture> getThursdayLectures() {
		List<Lecture> l = new LinkedList<>();
		l.add(new Lecture(9, 4, 3, new Course(1, "test", "descr test"), getListGrop()));
		l.add(new Lecture(4, 4, 3, new Course(2, "test1", "descr test"), getListGrop()));
		return l;
	}

	private List<Lecture> getFridayLectures() {
		List<Lecture> l = new LinkedList<>();
		l.add(new Lecture(10, 5, 4, new Course(1, "test", "descr test"), getListGrop()));
		l.add(new Lecture(5, 5, 4, new Course(2, "test1", "descr test"), getListGrop()));
		return l;
	}

	private List<Group> getListGrop() {
		List<Group> g = new LinkedList<>();
		g.add(new Group(1, "XA-11"));
		g.add(new Group(2, "Xb-12"));
		g.add(new Group(1, "Xc-13"));
		return g;
	}

}
