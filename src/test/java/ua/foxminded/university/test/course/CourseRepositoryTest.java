package ua.foxminded.university.test.course;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.university.entity.Course;
import ua.foxminded.university.repository.CourseRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		CourseRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/drop_schema_if_exists.sql", "/sql/create_new_schema.sql", "/sql/create_new_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CourseRepositoryTest {

	@Autowired
	private CourseRepository courseRepository;

	@Test
	void testGetById() {
		Optional<Course> optionalCourse = courseRepository.findById((long) 1);

		Course actualCourse = optionalCourse.get();

		assertThat(optionalCourse).isNotNull();
		assertThat(optionalCourse.get()).isInstanceOf(Course.class);
		assertThat(actualCourse.getName().strip()).isEqualTo("Physics");
	}

	@Test
	void testGetCourses() {
		List<Course> actualResult = courseRepository.findAll();

		assertThat(actualResult).isNotNull().hasSize(2);

		assertThat(actualResult.get(0).getName().trim()).isEqualTo("Physics");
		assertThat(actualResult.get(1).getName().trim()).isEqualTo("Literature");
	}

	@Test
	void testGetCourseLectures() {
		Optional<Course> course = courseRepository.findById((long) 1);

		int expectedResult = 11;
		int actualResult = course.get().getLectures().size();
		assertThat(course).isNotEmpty();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testSave() {
		List<Course> expectedResult = new ArrayList<>();
		expectedResult.add(new Course(3, "test", "test"));
		expectedResult.add(new Course(4, "test2", "test2"));

		List<Course> actualResult = courseRepository.saveAll(expectedResult);

		assertEquals(expectedResult, actualResult);
	}
}