package ua.foxminded.university.test.lecture;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
import ua.foxminded.university.controller.LectureController;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStudent;
import ua.foxminded.university.service.impl.CourseServiceImpl;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.LectureServiceImpl;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(LectureController.class)
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureServiceImpl lectureService;
    @MockBean
    private CourseServiceImpl courseService;
    @MockBean
    private GroupServiceImpl groupService;

    private final List<Course> courses = getCoursesForTests();
    private final List<Group> groups = getGroupForTest();
    private final List<Lecture> lectures = getLecturesForTest();


    @Test
    @WithMockRoleAdmin
    void testGetAllLecturesWithMockRoleAdmin() throws Exception {
        Mockito.when(lectureService.getAllLectures()).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lectures/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lectures available at database."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    @WithMockRoleLecturer
    void testGetAllLecturesWithMockRoleLecturer() throws Exception {
        Mockito.when(lectureService.getAllLectures()).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lectures/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lectures available at database."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    @WithMockRoleStudent
    void testGetAllLecturesWithMockRoleStudent() throws Exception {
        Mockito.when(lectureService.getAllLectures()).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lectures/all"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testGetAllLecturesWithAnonymousUser() throws Exception {
        Mockito.when(lectureService.getAllLectures()).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lectures/all"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockRoleAdmin
    void testGetLectureByIdWithMockRoleAdmin() throws Exception {
        Mockito.when(lectureService.getLecture(1)).thenReturn(Arrays.asList(lectures.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/lectures/?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", Arrays.asList(lectures.get(0))));
    }

    @Test
    @WithMockRoleLecturer
    void testGetLectureByIdWithMockRoleLecturer() throws Exception {
        Mockito.when(lectureService.getLecture(1)).thenReturn(Arrays.asList(lectures.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/lectures/?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", Arrays.asList(lectures.get(0))));
    }

    @Test
    @WithMockRoleStudent
    void testGetLectureByIdWithMockRoleStudent() throws Exception {
        Mockito.when(lectureService.getLecture(1)).thenReturn(Arrays.asList(lectures.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/lectures/?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", Arrays.asList(lectures.get(0))));
    }

    @Test
    void testGetLectureByIdWithAnonymousUser() throws Exception {
        Mockito.when(lectureService.getLecture(1)).thenReturn(Arrays.asList(lectures.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/lectures/?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testGetLecturesForCourseWithMockRoleAdmin() throws Exception {
        Mockito.when(courseService.getCourseLectures(1)).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lectures/course-lectures?courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lectures for course with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    @WithMockRoleLecturer
    void testGetLecturesForCourseWithMockRoleLecturer() throws Exception {
        Mockito.when(courseService.getCourseLectures(1)).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lectures/course-lectures?courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lectures for course with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    @WithMockRoleStudent
    void testGetLecturesForCourseWithMockRoleStudent() throws Exception {
        Mockito.when(courseService.getCourseLectures(1)).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lectures/course-lectures?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testGetLecturesForCourseWithAnonymousUser() throws Exception {
        Mockito.when(courseService.getCourseLectures(1)).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturer/lectures/course-lectures?courseId=1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testGetLecturesForGroupWithMockRoleAdmin() throws Exception {
        Mockito.when(groupService.getGroupLectures(1)).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/lectures/group-lectures?groupId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lectures for group with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    @WithMockRoleLecturer
    void testGetLecturesForGroupWithMockRoleLecturer() throws Exception {
        Mockito.when(groupService.getGroupLectures(1)).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/lectures/group-lectures?groupId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lectures for group with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    @WithMockRoleStudent
    void testGetLecturesForGroupWithMockRoleStudent() throws Exception {
        Mockito.when(groupService.getGroupLectures(1)).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/lectures/group-lectures?groupId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lectures for group with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    void testGetLecturesForGroupWithAnonymousUser() throws Exception {
        Mockito.when(groupService.getGroupLectures(1)).thenReturn(lectures);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/lectures/group-lectures?groupId=1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    //	Data for Tests
    private List<Lecture> getLecturesForTest() {
        List<Lecture> lectures = new LinkedList<>();
        lectures.add(new Lecture(1, 1, 1, courses.get(0), groups));
        lectures.add(new Lecture(2, 3, 3, courses.get(1), groups));
        lectures.add(new Lecture(3, 3, 4, courses.get(2), groups));
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