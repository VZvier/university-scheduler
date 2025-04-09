package ua.foxminded.university.test.weeklyschedule;

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
import ua.foxminded.university.controller.WeeklyScheduleController;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStudent;
import ua.foxminded.university.service.DaiLyScheduleService;

import java.util.LinkedList;
import java.util.List;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(WeeklyScheduleController.class)
class WeeklyScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DaiLyScheduleService dSS;

    private final List<Lecture> testLectures = getLecturesForTest();

    @Test
    @WithMockRoleAdmin
    void testStudentScheduleWithMockRoleAdmin() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/students-weekly-timetable?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Weekly Timetable"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Common schedule for period a week."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", testLectures));
    }

    @Test
    @WithMockRoleLecturer
    void testStudentScheduleWithMockRoleLecturer() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/students-weekly-timetable?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Weekly Timetable"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Common schedule for period a week."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", testLectures));
    }

    @Test
    @WithMockRoleStudent
    void testStudentScheduleWithMockRoleStudent() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/students-weekly-timetable?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Weekly Timetable"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Common schedule for period a week."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", testLectures));
    }

    @Test
    void testStudentScheduleWithAnonymous() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/students-weekly-timetable?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testLecturerScheduleWithMockRoleAdmin() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lecturers-weekly-timetable?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Weekly Timetable"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Common schedule for period a week."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", testLectures));
    }

    @Test
    @WithMockRoleLecturer
    void testLecturerScheduleWithMockRoleLecturer() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lecturers-weekly-timetable?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Weekly Timetable"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Common schedule for period a week."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", testLectures));
    }

    @Test
    @WithMockRoleStudent
    void testLecturerScheduleWithMockRoleStudent() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lecturers-weekly-timetable?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testLecturerScheduleWithAnonymous() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lecturers-weekly-timetable?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testCommonScheduleWithMockRoleAdmin() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/common-weekly-timetable"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "COMMON TIMETABLE"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Common schedule for period a week."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", testLectures));
    }

    @Test
    @WithMockRoleLecturer
    void testCommonScheduleWithMockRoleLecturer() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/common-weekly-timetable"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/schedule"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "COMMON TIMETABLE"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Common schedule for period a week."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", testLectures));
    }

    @Test
    @WithMockRoleStudent
    void testCommonScheduleWithMockRoleStudent() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/common-weekly-timetable"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testCommonScheduleWithAnonymous() throws Exception {
        Mockito.when(dSS.getLectures(dSS.getDailyScheduleForStudent(1))).thenReturn(testLectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/common-weekly-timetable"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    //	Data for Tests
    private List<Lecture> getLecturesForTest(){
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

    private List<Course> getCoursesForTests(){
        List<Course> course = new LinkedList<>();
        course.add(new Course(1, "Test1", "Some Description"));
        course.add(new Course(2, "Test2", "Some Description"));
        course.add(new Course(3, "Test3", "Some Description"));
        return course;
    }
}