package ua.foxminded.university.test.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.repository.StudentRepository;
import ua.foxminded.university.service.impl.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private StudentServiceImpl studentService;

	@Test
	void testGetStudents() {
		List<Student> expectedResult = getStudentsList();

		when(studentRepository.findAll()).thenReturn(expectedResult);
		List<Student> actualResult = studentService.getAllStudents();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetStudentById() {
		long id = anyLong();
		List<Student> expectedResult = getOneStudentAsList();

		when(studentRepository.findById(id)).thenReturn(Optional.of(expectedResult.get(0)));
		List<Student> actualResult = studentService.getStudent((int) id);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetLastStudentId() {
		List<Student> students = getStudentsList();
		int expectedResult = students.size();

		when(studentRepository.getLastStudentsId()).thenReturn(students.size());
		int actualResult = (int) studentService.getLastStudentId();

		assertEquals(expectedResult, actualResult);
	}

// Methods gives data for mock Dao-classes-------------
	private List<Student> getStudentsList() {
		List<Student> students = new ArrayList<>();
		students.add(Student.builder().id((long) 1).group(new Group(1, "XA-45"))
				.firstName("Ivan").lastName("Ivanov").build());
		students.add(Student.builder().id((long) 2).group(new Group(2, "XB-11"))
				.firstName("Sergey").lastName("Petrov").build());
		students.add(Student.builder().id((long) 3).group(new Group(3, "XS-58"))
				.firstName("Petr").lastName("Sergeyev").build());
		return students;
	}

	private List<Student> getOneStudentAsList() {
		List<Student> students = new ArrayList<>();
		students.add(Student.builder().id((long) 1)
				.group(new Group(1, "XA-45")).firstName("Ivan").lastName("Ivanov").build());
		return students;
	}
}
