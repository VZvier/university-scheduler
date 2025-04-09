package ua.foxminded.university.test.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
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
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.service.impl.GroupServiceImpl;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@Mock
	private GroupRepository groupRepository;

	@InjectMocks
	private GroupServiceImpl groupService;

	@Test
	void testGetGroups() {
		List<Group> expectedResult = getGroupsList();

		when(groupRepository.findAll()).thenReturn(expectedResult);
		List<Group> actualResult = groupService.getAllGroups();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetById() {
		long groupNumber = anyLong();
		Group group = getGroupAsList().get(0);
		List<Group> expectedResult = new LinkedList<>();
		expectedResult.add(group);

		when(groupRepository.findById(groupNumber)).thenReturn(Optional.of(group));
		List<Group> actualResult = groupService.getGroup((int) groupNumber);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetGroupLectures() {
		long groupId = anyLong();
		List<Lecture> lectures = getLectures();
		Group group = getGroupsList().get(0);

		List<Lecture> expectedResult = new LinkedList<>();
		expectedResult.add(lectures.get(0));
		expectedResult.add(lectures.get(2));
		expectedResult.add(lectures.get(5));

		when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
		List<Lecture> actualResult = groupService.getGroupLectures((int) groupId);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetGroupCourses(){
		Group group = getGroupAsList().get(0);
		List<Course> expectedResult = getCourses();
		group.setCourses(expectedResult);

		when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
		List<Course> actualResult =groupService.getGroupCourses(1);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetLastGroupId(){
		int expectedResult = 1;

		when(groupService.getLastGroupId()).thenReturn(expectedResult);
		int actualResult = groupService.getLastGroupId();

		assertEquals(expectedResult, actualResult);
	}


	// Methods gives data for mock Dao-classes ---------------------------------
	private List<Group> getGroupsList() {
		List<Lecture> l = getLectures();
		List<Lecture> lec = new LinkedList<>();
		List<Group> g = new ArrayList<>();
		lec.add(l.get(0));
		lec.add(l.get(2));
		lec.add(l.get(5));
		g.add(Group.builder().id(1).name("XA-45").lectures(lec).build());
		lec = new LinkedList<>();
		lec.add(l.get(1));
		lec.add(l.get(3));
		lec.add(l.get(6));
		g.add(Group.builder().id(2).name("XB-11").lectures(lec).build());
		lec = new LinkedList<>();
		lec.add(l.get(6));
		lec.add(l.get(0));
		lec.add(l.get(3));
		g.add(Group.builder().id(3).name("XS-58").lectures(lec).build());
		return g;
	}

	private List<Group> getGroupAsList() {
		List<Group> groups = new ArrayList<>();
		groups.add(Group.builder().id(1).name("XA-45").build());
		return groups;
	}

	private List<Lecture> getLectures() {
		List<Lecture> lectures = new LinkedList<>();
		lectures.add(new Lecture(1, 1, 1, null, null));
		lectures.add(new Lecture(2, 2, 2, null, null));
		lectures.add(new Lecture(3, 3, 1, null, null));
		lectures.add(new Lecture(4, 1, 2, null, null));
		lectures.add(new Lecture(5, 2, 1, null, null));
		lectures.add(new Lecture(6, 3, 2, null, null));
		lectures.add(new Lecture(7, 4, 3, null, null));
		return lectures;
	}

	private List<Course> getCourses(){
		List<Course> courses = new LinkedList<>();
		courses.add(Course.builder().id(1).name("TestCourse1").description("TestDescription").build());
		courses.add(Course.builder().id(2).name("TestCourse2").description("TestDescription").build());
		courses.add(Course.builder().id(3).name("TestCourse3").description("TestDescription").build());

		return courses;
	}
}
