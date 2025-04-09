package ua.foxminded.university.test.lecturer;

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
import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.repository.LecturerRepository;
import ua.foxminded.university.service.impl.LecturerServiceImpl;

@ExtendWith(MockitoExtension.class)
class LecturerServiceTest {

	@Mock
	private LecturerRepository lecturerRepository;

	@InjectMocks
	private LecturerServiceImpl lecturerService;

	@Test
	void testGetAllLecturers() {
		List<Lecturer> expectedResult = getLecturersWithCoursesList();

		when(lecturerRepository.findAll()).thenReturn(expectedResult);
		List<Lecturer> actualResult = lecturerService.getAllLecturers();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetLecturerById() {
		Lecturer expectedResult = getLecturersWithCoursesList().get(0);

		when(lecturerRepository.findById((long) 1)).thenReturn(Optional.of(expectedResult));
		Lecturer actualResult = lecturerService.getLecturer(1).get(0);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetLecturerCourse() {
		Lecturer lecturer = getLecturersWithCoursesList().get(0);
		Course expectedResult = lecturer.getCourse();

		when(lecturerRepository.findById((long) 1)).thenReturn(Optional.of(lecturer));
		Course actualResult = lecturerService.getLecturerCourse(1).get(0);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetLastId() {
		List<Lecturer> lecturer = getLecturersWithCoursesList();
		int expectedResult = lecturer.size();

		when(lecturerRepository.getLastLecturersId()).thenReturn(lecturer.size());
		int actualResult = lecturerService.getLastLecturerId();

		assertEquals(expectedResult, actualResult);
	}

// Methods gives data for mock Dao-classes--------------------------------------------------------------
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
}
