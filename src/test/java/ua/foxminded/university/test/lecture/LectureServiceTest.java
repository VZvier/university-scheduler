package ua.foxminded.university.test.lecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.repository.LectureRepository;
import ua.foxminded.university.service.impl.LectureServiceImpl;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

	@Mock
	private LectureRepository lectureRepository;

	@InjectMocks
	private LectureServiceImpl lectureService;

	@Test
	void testGetAllLectures() {
		List<Lecture> expectedResult = getLectures();

		when(lectureRepository.findAll()).thenReturn(expectedResult);
		List<Lecture> actualResult = lectureService.getAllLectures();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetGeyId() {
		Lecture expectedResult = getLectures().get(0);

		when(lectureRepository.findById((long) 1)).thenReturn(Optional.of(expectedResult));
		Lecture actualResult = lectureService.getLecture(1).get(0);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetLectureCourse() {
		Lecture lecture = getLectures().get(0);
		List<Course> expectedResult = new ArrayList<>();
		expectedResult.add(lecture.getCourse());

		when(lectureRepository.findById((long) 1)).thenReturn(Optional.of(lecture));
		List<Course> actualResult = lectureService.getLectureCourse(1);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetLectureGroups() {
		Lecture lecture = getLectures().get(0);
		List<Group> expectedResult = new ArrayList<>();
		expectedResult.addAll(lecture.getGroups());

		when(lectureRepository.findById((long) 1)).thenReturn(Optional.of(lecture));
		List<Group> actualResult = lectureService.getLectureGroups(1);

		assertEquals(expectedResult, actualResult);
	}

//	Methods gives data for mock Dao-classes---------------------------------------
	private List<Lecture> getLectures() {
		List<Course> courses = getCourseList();
		List<Group> groups = getGroupsList();
		List<Lecture> lectures = new LinkedList<>();
		List<Group> g = new LinkedList<>();
		g.add(groups.get(0));
		g.add(groups.get(2));
		lectures.add(new Lecture(1, 1, 1, courses.get(1), g));
		g = new LinkedList<>();
		g.add(groups.get(1));
		lectures.add(new Lecture(2, 2, 2, courses.get(0), g));
		g = new LinkedList<>();
		g.add(groups.get(0));
		lectures.add(new Lecture(3, 3, 1, courses.get(2), g));
		g = new LinkedList<>();
		g.add(groups.get(1));
		g.add(groups.get(2));
		lectures.add(new Lecture(4, 1, 2, courses.get(1), g));
		g = new LinkedList<>();
		g.add(groups.get(1));
		lectures.add(new Lecture(5, 2, 1, courses.get(2), g));
		g = new LinkedList<>();
		g.add(groups.get(0));
		lectures.add(new Lecture(6, 3, 2, courses.get(0), g));
		g = new LinkedList<>();
		g.add(groups.get(2));
		lectures.add(new Lecture(7, 4, 3, courses.get(1), g));

		return lectures;
	}

	private List<Course> getCourseList() {
		List<Course> course = new ArrayList<>();
		course.add(new Course(1, "Mathematics", "Math description"));
		course.add(new Course(2, "Literature", "World literature"));
		course.add(new Course(3, "Phisics", "Since about nature"));
		return course;
	}

	private List<Group> getGroupsList() {
		List<Group> g = new ArrayList<>();
		g.add(Group.builder().id(1).name("XA-45").build());
		g.add(Group.builder().id(2).name("XB-11").build());
		g.add(Group.builder().id(3).name("XS-58").build());
		return g;
	}
}
