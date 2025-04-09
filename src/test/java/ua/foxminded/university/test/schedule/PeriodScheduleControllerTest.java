package ua.foxminded.university.test.schedule;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.configuration.DefaultSecurityConfig;
import ua.foxminded.university.controller.PeriodScheduleController;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStudent;
import ua.foxminded.university.service.impl.DailyScheduleServiceImpl;
import ua.foxminded.university.service.impl.PeriodScheduleServiceImpl;

import java.util.LinkedList;
import java.util.List;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(PeriodScheduleController.class)
class PeriodScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PeriodScheduleServiceImpl periodScheduleService;
	@MockBean
	private DailyScheduleServiceImpl dailyScheduleService;


	@Test
	@WithMockRoleAdmin
	void testGetCommonScheduleWithMockRoleAdmin() throws Exception {
		Mockito.when(periodScheduleService.getLectures((periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getCommonDailySchedule())))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/lecturer/common-period-timetable?days=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "COMMON TIMETABLE"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Common schedule for period = " + 1 + " days."))
				.andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
				.andExpect(MockMvcResultMatchers.model().attribute("listLectures", getLecturesForTest()));
	}

	@Test
	@WithMockRoleLecturer
	void testGetCommonScheduleWithMockRoleLecturer() throws Exception {
		Mockito.when(periodScheduleService.getLectures((periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getCommonDailySchedule())))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/lecturer/common-period-timetable?days=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "COMMON TIMETABLE"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Common schedule for period = " + 1 + " days."))
				.andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
				.andExpect(MockMvcResultMatchers.model().attribute("listLectures", getLecturesForTest()));
	}

	@Test
	@WithMockRoleStudent
	void testGetCommonScheduleWithMockRoleStudent() throws Exception {
		Mockito.when(periodScheduleService.getLectures((periodScheduleService.getPeriodSchedule(1,
						dailyScheduleService.getCommonDailySchedule()))))
				.thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/lecturer/common-period-timetable?days=1"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void testGetCommonScheduleWithAnonymousUser() throws Exception {
		Mockito.when(periodScheduleService.getLectures((periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getCommonDailySchedule())))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/lecturer/common-period-timetable?days=1"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockRoleAdmin
	void testGetScheduleForStudentWithMockRoleAdmin() throws Exception {
		Mockito.when(periodScheduleService.getLectures(periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getDailyScheduleForStudent(1)))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/common/students-period-timetable?days=1&studentId=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "STUDENT'S TIMETABLE"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Schedule for student with id = " + 1 + " for period = " + 1 + " days."))
				.andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
				.andExpect(MockMvcResultMatchers.model().attribute("listLectures", getLecturesForTest()));
	}

	@Test
	@WithMockRoleLecturer
	void testGetScheduleForStudentWithMockRoleLecturer() throws Exception {
		Mockito.when(periodScheduleService.getLectures(periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getDailyScheduleForStudent(1)))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/common/students-period-timetable?days=1&studentId=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "STUDENT'S TIMETABLE"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Schedule for student with id = " + 1 + " for period = " + 1 + " days."))
				.andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
				.andExpect(MockMvcResultMatchers.model().attribute("listLectures", getLecturesForTest()));
	}

	@Test
	@WithMockRoleStudent
	void testGetScheduleForStudentWithMockRoleStudent() throws Exception {
		Mockito.when(periodScheduleService.getLectures(periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getDailyScheduleForStudent(1)))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/common/students-period-timetable?days=1&studentId=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "STUDENT'S TIMETABLE"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Schedule for student with id = " + 1 + " for period = " + 1 + " days."))
				.andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
				.andExpect(MockMvcResultMatchers.model().attribute("listLectures", getLecturesForTest()));
	}

	@Test
	void testGetScheduleForStudentWithAnonymousUser() throws Exception {
		Mockito.when(periodScheduleService.getLectures(periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getDailyScheduleForStudent(1)))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/common/students-period-timetable?days=1&studentId=1"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockRoleAdmin
	void testGetScheduleLecturerWithMockRoleAdmin() throws Exception {
		Mockito.when(periodScheduleService.getLectures((periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getCommonDailySchedule())))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/lecturer/lecturers-period-timetable?days=1&lecturerId=1"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "LECTURER'S TIMETABLE"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Schedule for lecturer with id = " + 1 + " for period = " + 1 + " days."))
				.andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
				.andExpect(MockMvcResultMatchers.model().attribute("listLectures", getLecturesForTest()));
	}

	@Test
	@WithMockRoleLecturer
	void testGetScheduleLecturerWithMockRoleLecturer() throws Exception {
		Mockito.when(periodScheduleService.getLectures((periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getCommonDailySchedule())))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/lecturer/lecturers-period-timetable?days=1&lecturerId=1"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "LECTURER'S TIMETABLE"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Schedule for lecturer with id = " + 1 + " for period = " + 1 + " days."))
				.andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
				.andExpect(MockMvcResultMatchers.model().attribute("listLectures", getLecturesForTest()));
	}

	@Test
	@WithMockRoleStudent
	void testGetScheduleLecturerWithMockRoleStudent() throws Exception {
		Mockito.when(periodScheduleService.getLectures((periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getCommonDailySchedule())))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/lecturer/lecturers-period-timetable?days=1&lecturerId=1"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void testGetScheduleLecturerWithAnonymousUser() throws Exception {
		Mockito.when(periodScheduleService.getLectures((periodScheduleService.getPeriodSchedule(1,
				dailyScheduleService.getCommonDailySchedule())))).thenReturn(getLecturesForTest());

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/lecturer/lecturers-period-timetable?days=1&lecturerId=1"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}


	//	Data for Tests
	private List<Lecture> getLecturesForTest() {
		List<Lecture> lectures = new LinkedList<>();
		List<Course> courses = getCoursesForTests();
		List<Group> groups = getGroupForTest();
		lectures.add(new Lecture(1, 1, 1, courses.get(0), groups));
		lectures.add(new Lecture(2, 1, 2, courses.get(0), groups));
		lectures.add(new Lecture(3, 2, 1, courses.get(1), groups));
		lectures.add(new Lecture(4, 2, 2, courses.get(1), groups));
		lectures.add(new Lecture(5, 3, 1, courses.get(2), groups));
		lectures.add(new Lecture(6, 3, 3, courses.get(2), groups));
		lectures.add(new Lecture(7, 4, 3, courses.get(0), groups));
		lectures.add(new Lecture(8, 4, 4, courses.get(0), groups));
		lectures.add(new Lecture(9, 5, 1, courses.get(1), groups));
		lectures.add(new Lecture(10, 5, 4, courses.get(1), groups));
		return lectures;
	}

	private List<Group> getGroupForTest() {
		List<Group> groups = new LinkedList<>();
		groups.add(new Group(1, "XX-11"));
		groups.add(new Group(2, "XX-12"));
		return groups;
	}

	private List<Course> getCoursesForTests() {
		List<Course> course = new LinkedList<>();
		course.add(new Course(1, "Test1", "Some Description"));
		course.add(new Course(2, "Test2", "Some Description"));
		course.add(new Course(3, "Test3", "Some Description"));
		return course;
	}
}