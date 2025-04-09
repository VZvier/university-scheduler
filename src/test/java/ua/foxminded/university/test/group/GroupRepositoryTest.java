package ua.foxminded.university.test.group;

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

import ua.foxminded.university.entity.Group;
import ua.foxminded.university.repository.GroupRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		GroupRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/drop_schema_if_exists.sql", "/sql/create_new_schema.sql", "/sql/create_new_tables.sql",
		"/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GroupRepositoryTest {

	@Autowired
	private GroupRepository groupRepository;

	@Test
	void testGetById() {
		Optional<Group> group = groupRepository.findById((long) 1);

		int actualResult = (int) group.get().getId();
		int expectedResult = 1;

		assertThat(group.get().getName().strip()).isEqualTo("39-UX");
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetGroups() {
		List<Group> actualResult = groupRepository.findAll();

		assertThat(actualResult).isNotNull().hasSize(2);
	}

	@Test
	void testAddGroups() {
		List<Group> expectedResult = new ArrayList<>();
		expectedResult.add(Group.builder().id(1).name("39-UX").build());
		expectedResult.add(Group.builder().id(2).name("91-HZ").build());

		List<Group> actualResult = groupRepository.saveAll(expectedResult);

		assertThat(actualResult).isNotNull().hasSize(2);
		assertEquals(expectedResult, actualResult);
	}
}