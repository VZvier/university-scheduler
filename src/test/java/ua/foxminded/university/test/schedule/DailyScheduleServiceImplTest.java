package ua.foxminded.university.test.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.repository.LectureRepository;
import ua.foxminded.university.service.data.ObjectListToStringConverter;
import ua.foxminded.university.service.impl.DailyScheduleServiceImpl;
import ua.foxminded.university.service.impl.LecturerServiceImpl;
import ua.foxminded.university.service.impl.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
class DailyScheduleServiceImplTest {

	@Mock
	private StudentServiceImpl studentService;

	@Mock
	private LecturerServiceImpl lecturerService;

	@Mock
	private LectureRepository lectureRepository;

	@InjectMocks
	private DailyScheduleServiceImpl dailyScheduleService;

	private ObjectListToStringConverter converter = new ObjectListToStringConverter();

	@Test
	void testGetDailyScheduleForStudent() {
		String expectedResult = """
				ID=1, Day - MONDAY, Pair=4, Time: 13:30-14:50, Groups - 1, 2, Course - Literature
				 ID=1, Day - MONDAY, Pair=4, Time: 13:30-14:50, Groups - 1, 2, Course - Literature
				 ID=2, Day - TUESDAY, Pair=1, Time: 09:00-10:20, Groups - 2, 3, Course - Mathematics
				 ID=3, Day - WEDNESDAY, Pair=2, Time: 10:30-11:50, Groups - 3, 1, Course - Physics
				 ID=3, Day - WEDNESDAY, Pair=2, Time: 10:30-11:50, Groups - 1, 3, Course - Physics
				 ID=4, Day - THURSDAY, Pair=3, Time: 12:00-13:20, Groups - 1, 2, Course - Literature
				 ID=4, Day - THURSDAY, Pair=3, Time: 12:00-13:20, Groups - 1, 2, Course - Literature
				 ID=5, Day - FRIDAY, Pair=4, Time: 13:30-14:50, Groups - 3, 1, Course - Physics
				 ID=5, Day - FRIDAY, Pair=4, Time: 13:30-14:50, Groups - 1, 3, Course - Physics
				 ID=6, Day - MONDAY, Pair=4, Time: 13:30-14:50, Groups - 2, 3, Course - Mathematics
				 ID=7, Day - TUESDAY, Pair=1, Time: 09:00-10:20, Groups - 1, 2, Course - Physics
				 ID=7, Day - TUESDAY, Pair=1, Time: 09:00-10:20, Groups - 1, 2, Course - Physics
				 ID=8, Day - WEDNESDAY, Pair=2, Time: 10:30-11:50, Groups - 3, 1, Course - Literature
				 ID=8, Day - WEDNESDAY, Pair=2, Time: 10:30-11:50, Groups - 1, 3, Course - Literature
				 ID=9, Day - THURSDAY, Pair=3, Time: 12:00-13:20, Groups - 2, 3, Course - Mathematics
				 ID=10, Day - FRIDAY, Pair=4, Time: 13:30-14:50, Groups - 3, 1, Course - Literature
				 ID=10, Day - FRIDAY, Pair=4, Time: 13:30-14:50, Groups - 1, 3, Course - Literature""";
		List<Student> student = getStudentWithLecturesInItsGroup();

		when(studentService.getStudent(1)).thenReturn(student);
		when(lectureRepository.findLecturesByGroupsAndDayNumber(student.get(0).getGroup(), 1))
				.thenReturn(student.get(0).getGroup().getLectures());
		String actualResult = converter.convertShedule(dailyScheduleService.getDailyScheduleForStudent(1)).strip();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetDailyScheduleForLecturer() {
		String expectedResult = """
				ID=1, Day - MONDAY, Pair=4, Time: 13:30-14:50, Groups - 1, 2, Course - Literature
				 ID=2, Day - TUESDAY, Pair=1, Time: 09:00-10:20, Groups - 2, 3, Course - Mathematics
				 ID=3, Day - WEDNESDAY, Pair=2, Time: 10:30-11:50, Groups - 3, 1, Course - Physics
				 ID=4, Day - THURSDAY, Pair=3, Time: 12:00-13:20, Groups - 1, 2, Course - Literature
				 ID=5, Day - FRIDAY, Pair=4, Time: 13:30-14:50, Groups - 3, 1, Course - Physics
				 ID=6, Day - MONDAY, Pair=4, Time: 13:30-14:50, Groups - 2, 3, Course - Mathematics
				 ID=7, Day - TUESDAY, Pair=1, Time: 09:00-10:20, Groups - 1, 2, Course - Physics
				 ID=8, Day - WEDNESDAY, Pair=2, Time: 10:30-11:50, Groups - 3, 1, Course - Literature
				 ID=9, Day - THURSDAY, Pair=3, Time: 12:00-13:20, Groups - 2, 3, Course - Mathematics
				 ID=10, Day - FRIDAY, Pair=4, Time: 13:30-14:50, Groups - 3, 1, Course - Literature""";
		List<Lecturer> l = getLecturerWithHisLecturesOnCourse();

		when(lecturerService.getLecturer(1)).thenReturn(l);
		when(lectureRepository.findLecturesByCourseIdAndDayNumber(1, 1)).thenReturn(l.get(0).getCourse().getLectures());
		String actualResult = converter.convertShedule(dailyScheduleService.getDailyScheduleForLecturer(1)).strip();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetGeneralDailySchedule() {
		String expectedResult = """
				ID=1, Day - MONDAY, Pair=4, Time: 13:30-14:50, Groups - 1, 2, Course - Literature
				 ID=2, Day - TUESDAY, Pair=1, Time: 09:00-10:20, Groups - 2, 3, Course - Mathematics
				 ID=3, Day - WEDNESDAY, Pair=2, Time: 10:30-11:50, Groups - 3, 1, Course - Physics
				 ID=4, Day - THURSDAY, Pair=3, Time: 12:00-13:20, Groups - 1, 2, Course - Literature
				 ID=5, Day - FRIDAY, Pair=4, Time: 13:30-14:50, Groups - 3, 1, Course - Physics
				 ID=6, Day - MONDAY, Pair=4, Time: 13:30-14:50, Groups - 2, 3, Course - Mathematics
				 ID=7, Day - TUESDAY, Pair=1, Time: 09:00-10:20, Groups - 1, 2, Course - Physics
				 ID=8, Day - WEDNESDAY, Pair=2, Time: 10:30-11:50, Groups - 3, 1, Course - Literature
				 ID=9, Day - THURSDAY, Pair=3, Time: 12:00-13:20, Groups - 2, 3, Course - Mathematics
				 ID=10, Day - FRIDAY, Pair=4, Time: 13:30-14:50, Groups - 3, 1, Course - Literature""";
		List<Lecture> lectures = getLectures();

		when(lectureRepository.findLectureByDayNumber(1)).thenReturn(lectures);
		String actualResult = converter.convertShedule(dailyScheduleService.getCommonDailySchedule()).strip();

		assertEquals(expectedResult, actualResult);
	}

//	Methods gives data for mock Dao-classes-------------------------------
	private List<Lecture> getLectures() {
		List<Course> courses = getCourseList();
		List<Lecture> lectures = new LinkedList<>();
		List<Group> gr = new LinkedList<>();
		List<Group> g = new LinkedList<>();
		g.add(new Group(1, "XA-45"));
		g.add(new Group(2, "XB-11"));
		g.add(new Group(3, "XS-58"));
		gr.add(g.get(2));
		gr.add(g.get(0));
		lectures.add(new Lecture(1, 1, 4, courses.get(1), g.subList(0, 2)));
		lectures.add(new Lecture(2, 2, 1, courses.get(0), g.subList(1, 3)));
		lectures.add(new Lecture(3, 3, 2, courses.get(2), gr));
		lectures.add(new Lecture(4, 4, 3, courses.get(1), g.subList(0, 2)));
		lectures.add(new Lecture(5, 5, 4, courses.get(2), gr));
		lectures.add(new Lecture(6, 1, 4, courses.get(0), g.subList(1, 3)));
		lectures.add(new Lecture(7, 2, 1, courses.get(2), g.subList(0, 2)));
		lectures.add(new Lecture(8, 3, 2, courses.get(1), gr));
		lectures.add(new Lecture(9, 4, 3, courses.get(0), g.subList(1, 3)));
		lectures.add(new Lecture(10, 5, 4, courses.get(1), gr));
		return lectures;
	}

	private List<Lecturer> getLecturersWithCoursesList() {
		List<Course> courses = getCourseList();
		List<Lecturer> lecturers = new LinkedList<>();
		lecturers.add(new Lecturer(1, "test", "test", courses.get(2), null));
		lecturers.add(new Lecturer(2, "test1", "test1", courses.get(1), null));
		lecturers.add(new Lecturer(3, "test2", "test2", courses.get(0), null));
		return lecturers;
	}

	private List<Course> getCourseList() {
		List<Course> course = new ArrayList<>();
		course.add(new Course(1, "Mathematics", "Math description"));
		course.add(new Course(2, "Literature", "World literature"));
		course.add(new Course(3, "Physics", "Since about nature"));
		return course;
	}

	private List<Student> getStudentsList() {
		List<Student> students = new ArrayList<>();
		students.add(Student.builder().id((long) 1).group(new Group(1, "XA-45")).firstName("Ivan").lastName("Ivanov")
				.build());
		students.add(Student.builder().id((long) 2).group(new Group(2, "XB-11")).firstName("Sergey").lastName("Petrov")
				.build());
		students.add(Student.builder().id((long) 3).group(new Group(3, "XS-58")).firstName("Petr").lastName("Sergeyev")
				.build());
		return students;
	}

	private List<Lecturer> getLecturerWithHisLecturesOnCourse() {
		Lecturer lecturer = getLecturersWithCoursesList().get(0);
		Course course = getCourseList().get(0);
		List<Lecture> lectures = getLectures();
		course.setLectures(lectures);
		lecturer.setCourse(course);
		List<Lecturer> l = new LinkedList<>();
		l.add(lecturer);
		return l;
	}

	private List<Student> getStudentWithLecturesInItsGroup() {
		List<Student> s = getStudentsList();
		List<Group> g = new LinkedList<>();
		g.add(new Group(1, "XA-45"));
		g.add(new Group(2, "XB-11"));
		g.add(new Group(3, "XS-58"));
		List<Course> courses = getCourseList();
		List<Lecture> lectures = getLectures();
		List<Group> gr = new LinkedList<>();
		s.add(new Student((long) 1, null, "test", "test", null));
		gr.add(g.get(0));
		gr.add(g.get(2));
		lectures.add(new Lecture(1, 1, 4, courses.get(1), g.subList(0, 2)));
		lectures.add(new Lecture(3, 3, 2, courses.get(2), gr));
		lectures.add(new Lecture(4, 4, 3, courses.get(1), g.subList(0, 2)));
		lectures.add(new Lecture(5, 5, 4, courses.get(2), gr));
		lectures.add(new Lecture(7, 2, 1, courses.get(2), g.subList(0, 2)));
		lectures.add(new Lecture(8, 3, 2, courses.get(1), gr));
		lectures.add(new Lecture(10, 5, 4, courses.get(1), gr));

		s.get(0).setGroup(null);
		s.add(new Student((long) 1, null, "test", "test", null));
		s.get(0).setGroup(Group.builder().id(1).name("XA-45").lectures(lectures).build());
		return s;
	}

}