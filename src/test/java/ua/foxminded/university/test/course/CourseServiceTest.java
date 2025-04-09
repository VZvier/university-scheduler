package ua.foxminded.university.test.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.impl.CourseServiceImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

	@Mock
	private CourseRepository courseRepository;

	@InjectMocks
	private CourseServiceImpl courseService;


	@Test
	void testGetAllCourses() {
		List<Course> expectedResult = getCoursesWithLecturersList();

		when(courseRepository.findAll()).thenReturn(expectedResult);
		List<Course> actualResult = courseService.getAllCourses();

		assertEquals(expectedResult, actualResult);

	}

	@Test
	void testGetById() {
		long courseNumber = anyLong();
		List<Course> expectedResult = new ArrayList<>();

		expectedResult.add(getCoursesWithLecturersList().get((int) courseNumber));
		when(courseRepository.findById(courseNumber)).thenReturn(Optional.of(expectedResult.get(0)));
		List<Course> actualResult = courseService.getCourseById((int) courseNumber);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetCourseLecturers() {
		long courseNumber = anyLong();
		List<Course> course = new ArrayList<>();
		course.add(getCoursesWithLecturersList().get((int) courseNumber));

		List<Lecturer> expectedResult = course.get(0).getLecturers();
		when(courseRepository.findById(courseNumber)).thenReturn(Optional.of(course.get(0)));
		List<Lecturer> actualResult = courseService.getCourseLecturers((int) courseNumber);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetCourseLectures() {
		long courseNumber = anyLong();
		List<Course> course = new ArrayList<>();
		course.add(getCoursesWithLecturersList().get((int) courseNumber));

		List<Lecture> expectedResult = course.get(0).getLectures();
		when(courseRepository.findById(courseNumber)).thenReturn(Optional.of(course.get(0)));
		List<Lecture> actualResult = courseService.getCourseLectures((int) courseNumber);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetCourseByName() {
		Course expectedResult = getCourseList().get(1);

		when(courseRepository.findByName(expectedResult.getName())).thenReturn(Optional.of(expectedResult));
		Course actualResult = courseService.getCourseByName(expectedResult.getName());

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetGroupsOnCourse(){
		List<Group> expectedResult = List.of(new Group(1, "test"));
		Course course = getCourseList().get(1);
		course.setGroups(List.of(expectedResult.get(0)));

		when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
		List<Group> actualResult = courseService.getGroupsOnCourse(1);

		assertEquals(expectedResult, actualResult);
	}


	@Test
	void testSaveUpdateCourses(){
		Course expectedResult = getCourseList().get(0);

		when(courseRepository.save(getCourseList().get(0))).thenReturn(getCourseList().get(0));
		Course actualResult = courseService.saveUpdateCourses(expectedResult);

		assertEquals(expectedResult, actualResult);
	}


	@Test
	void testGetLastCourseId(){
		int expectedResult = 1;

		when(courseRepository.getLastCourseId()).thenReturn(1);
		int actualResult = courseService.getLastCoursesId();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testClearTable(){
		boolean expectedResult = true;

		doNothing().when(courseRepository).deleteAll();
		boolean actualResult = courseService.clearCoursesTable();

		assertEquals(expectedResult, actualResult);
	}


	// Methods gives data for mock Dao-classes-------------------------------
	private List<Course> getCourseList() {
		List<Course> course = new ArrayList<>();
		course.add(new Course(1, "Mathematics", "Math description"));
		course.add(new Course(2, "Literature", "World literature"));
		course.add(new Course(3, "Phisics", "Since about nature"));
		return course;
	}

	private List<Lecturer> getLecturersWithCoursesList() {
		List<Course> courses = getCourseList();
		List<Lecturer> lecturers = new LinkedList<>();
		lecturers.add(new Lecturer(1, "test", "test", courses.get(2), null));
		lecturers.add(new Lecturer(2, "test1", "test1", courses.get(1), null));
		lecturers.add(new Lecturer(3, "test2", "test2", courses.get(0), null));
		return lecturers;
	}

	private List<Course> getCoursesWithLecturersList() {
		List<Lecture> lectures = getLectures();
		List<Course> courses = getCourseList();
		List<Lecturer> lecturers = getLecturersWithCoursesList();
		List<Lecturer> courseLecturers = new ArrayList<>();
		List<Lecture> courseLectures = new ArrayList<>();

		courseLecturers.add(lecturers.get(2));
		courses.get(0).setLecturers(courseLecturers);
		courseLectures.add(lectures.get(1));
		courseLectures.add(lectures.get(6));
		courses.get(0).setLectures(lectures);

		courseLectures = new LinkedList<>();
		courseLecturers = new ArrayList<>();
		courseLecturers.add(lecturers.get(1));
		courseLectures.add(lectures.get(0));
		courseLectures.add(lectures.get(3));
		courseLectures.add(lectures.get(6));
		courses.get(1).setLecturers(courseLecturers);
		courses.get(1).setLectures(courseLectures);

		courseLectures = new LinkedList<>();
		courseLecturers = new ArrayList<>();
		courseLecturers.add(lecturers.get(0));
		courseLectures.add(lectures.get(2));
		courseLectures.add(lectures.get(4));
		courses.get(2).setLecturers(courseLecturers);
		courses.get(2).setLectures(courseLectures);
		return courses;
	}

	private List<Lecture> getLectures() {
		List<Course> courses = getCourseList();
		List<Lecture> lectures = new LinkedList<>();
		lectures.add(new Lecture(1, 1, 1, courses.get(1), null));
		lectures.add(new Lecture(2, 2, 2, courses.get(0), null));
		lectures.add(new Lecture(3, 3, 1, courses.get(2), null));
		lectures.add(new Lecture(4, 1, 2, courses.get(1), null));
		lectures.add(new Lecture(5, 2, 1, courses.get(2), null));
		lectures.add(new Lecture(6, 3, 2, courses.get(0), null));
		lectures.add(new Lecture(7, 4, 3, courses.get(1), null));
		return lectures;
	}
}
