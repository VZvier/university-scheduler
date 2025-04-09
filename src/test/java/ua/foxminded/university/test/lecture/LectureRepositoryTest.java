package ua.foxminded.university.test.lecture;

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

import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.repository.LectureRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		LectureRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/drop_schema_if_exists.sql", "/sql/create_new_schema.sql", "/sql/create_new_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LectureRepositoryTest {

	@Autowired
	private LectureRepository lectureRepository;

	@Test
	void testFindById() {
		Optional<Lecture> lecture = lectureRepository.findById((long) 1);

		String actualResult = lecture.get().getDay().toString() + " " + lecture.get().getStart() + " "
				+ lecture.get().getEnd();
		String expectedResult = "MONDAY" + " " + "12:00 13:20";

		assertThat(lecture).isNotEmpty();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testFindAll() {
		List<Lecture> lectures = lectureRepository.findAll();

		int actualResult = lectures.size();
		int expectedResult = 24;

		assertThat(lectures).isNotEmpty();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testFindByCourseAndDayNumber() {
		List<Lecture> lectures = lectureRepository.findLecturesByCourseIdAndDayNumber(1, 1);

		int actualResult = lectures.size();
		int expectedResult = 11;

		assertThat(lectures).isNotEmpty();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testFindByGroupsAndDayNumber() {
		List<Lecture> lectures = lectureRepository.findLecturesByGroupsAndDayNumber(new Group(2, "91-HZ"), 2);

		int actualResult = lectures.size();
		int expectedResult = 3;

		assertThat(lectures).isNotEmpty();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testFindByDayNumber() {
		List<Lecture> lectures = lectureRepository.findLectureByDayNumber(5);

		int actualResult = lectures.size();
		int expectedResult = 1;

		assertThat(lectures).isNotEmpty();
		assertEquals(expectedResult, actualResult);
	}
}
