package ua.foxminded.university.test.lecturer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.repository.LecturerRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		LecturerRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/drop_schema_if_exists.sql", "/sql/create_new_schema.sql", "/sql/create_new_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LecturerRepositoryTest {

	@Autowired
	private LecturerRepository lecturerRepository;

	@Test
	void testFindById() {
		Optional<Lecturer> lecturerOptional = lecturerRepository.findById((long) 1);

		String actualResult = lecturerOptional.get().getFirstName().trim() + " "
				+ lecturerOptional.get().getLastName().trim();
		String expectedResult = "Jonathan" + " " + "Trann";

		assertThat(lecturerOptional).isNotNull();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testFindAllLecturers() {
		List<Lecturer> lecturers = lecturerRepository.findAll();

		int actualResult = lecturers.size();
		int expectedResult = 2;

		assertThat(lecturers).isNotEmpty();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testFindByCourse() {
		int actualResult = lecturerRepository.getLastLecturersId();
		int expectedResult = 2;

		assertEquals(expectedResult, actualResult);
	}
}
