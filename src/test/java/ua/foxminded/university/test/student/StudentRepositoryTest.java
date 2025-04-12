package ua.foxminded.university.test.student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.university.entity.Student;
import ua.foxminded.university.repository.StudentRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		StudentRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/drop_schema_if_exists.sql", "/sql/create_new_schema.sql", "/sql/create_new_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;

	@Test
	void testGetStudentsList() {
		List<Student> students = studentRepository.findAll();

		int actualResult = students.size();
		int expectedResult = 6;

		assertThat(students).isNotNull();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void tesFindStudentById() {
		Student actualResult = studentRepository.findById((long) 1).get();
		Student expectedResult = Student.builder().id((long) 1).group(null).firstName("Adriana   ")
				.lastName("Morgan    ").build();

		assertThat(actualResult).isNotNull();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testAddStudent() {
		Student students = Student.builder().id((long) 1).group(null).firstName("Adriana").lastName("Morgan").build();

		Student actualResult = studentRepository.save(students);
		Student expectedResult = students;

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testAddStudents() {
		List<Student> students = new ArrayList<>();
		students.add(Student.builder().id((long) 1).group(null).firstName("Adriana").lastName("Morgan").build());
		students.add(Student.builder().id((long) 2).group(null).firstName("Afina").lastName("Melosskaya").build());

		List<Student> actualResult = studentRepository.saveAll(students);
		List<Student> expectedResult = students;

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testRemoveStudentById() {
		studentRepository.deleteById((long) 1);

		boolean actualResult = studentRepository.existsById((long) 1);
		boolean expectedResult = false;

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetLastStudentId() {

		int actualResult = studentRepository.getLastStudentsId();
		int expectedResult = 6;

		assertEquals(expectedResult, actualResult);
	}
}