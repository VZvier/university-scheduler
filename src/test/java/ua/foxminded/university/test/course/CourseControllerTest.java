package ua.foxminded.university.test.course;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.configuration.DefaultSecurityConfig;
import ua.foxminded.university.controller.CourseController;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStaff;
import ua.foxminded.university.mockuser.WithMockRoleStudent;
import ua.foxminded.university.service.impl.CourseServiceImpl;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.LectureServiceImpl;
import ua.foxminded.university.service.impl.LecturerServiceImpl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseServiceImpl courseService;
    @MockBean
    private LecturerServiceImpl lecturerService;
    @MockBean
    private LectureServiceImpl lectureService;
    @MockBean
    private GroupServiceImpl groupService;

    @Test
    @WithMockRoleAdmin
    void testFindAllCoursesWithRoleAdmin() throws Exception {
        when(courseService.getAllCourses()).thenReturn(getCoursesForTests());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All courses present in the database."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCoursesForTests()));
    }

    @Test
    @WithMockRoleStudent
    void testFindAllCoursesWithRoleStudent() throws Exception {
        when(courseService.getAllCourses()).thenReturn(getCoursesForTests());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All courses present in the database."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCoursesForTests()));
    }

    @Test
    @WithMockRoleLecturer
    void testFindAllCoursesWithRoleLecturer() throws Exception {
        when(courseService.getAllCourses()).thenReturn(getCoursesForTests());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All courses present in the database."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCoursesForTests()));
    }

    @Test
    void testFindAllCoursesWithAnonymous() throws Exception {
        when(courseService.getAllCourses()).thenReturn(getCoursesForTests());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All courses present in the database."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCoursesForTests()));
    }


    @Test
    @WithMockRoleAdmin
    void testFindCourseByIdWithMockRoleAdmin() throws Exception {
        when(courseService.getCourseById(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    @WithMockRoleLecturer
    void testFindCourseByIdWithMockRoleLecturer() throws Exception {
        when(courseService.getCourseById(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    @WithMockRoleStudent
    void testFindCourseByIdWithMockRoleStudent() throws Exception {
        when(courseService.getCourseById(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    void testFindCourseByIdWithAnonymous() throws Exception {
        when(courseService.getCourseById(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockRoleAdmin
    void testFindCourseOfLecturerWithMockRoleAdmin() throws Exception {
        when(lecturerService.getLecturerCourse(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/lecturer-course?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course taught by the Lecturer with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    @WithMockRoleLecturer
    void testFindCourseOfLecturerWithMockRoleLecturer() throws Exception {
        when(lecturerService.getLecturerCourse(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/lecturer-course?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course taught by the Lecturer with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    @WithMockRoleStudent
    void testFindCourseOfLecturerWithMockRoleStudent() throws Exception {
        when(lecturerService.getLecturerCourse(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/lecturer-course?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course taught by the Lecturer with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    @WithAnonymousUser
    void testFindCourseOfLecturerWithAnonymous() throws Exception {
        when(lecturerService.getLecturerCourse(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/lecturer-course?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testFindCourseOfLectureWithMockRoleAdmin() throws Exception {
        when(lectureService.getLectureCourse(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/lecture-course?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course taught in lecture with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    @WithMockRoleLecturer
    void testFindCourseOfLectureWithMockRoleLecturer() throws Exception {
        when(lectureService.getLectureCourse(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/lecture-course?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course taught in lecture with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    @WithMockRoleStudent
    void testFindCourseOfLectureWithMockRoleStudent() throws Exception {
        when(lectureService.getLectureCourse(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/lecture-course?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course taught in lecture with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(0))));
    }

    @Test
    void testFindCourseOfLectureWithAnonymous() throws Exception {
        when(lectureService.getLectureCourse(1)).thenReturn(List.of(getCoursesForTests().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/lecture-course?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testFindGroupCoursesWithMockRoleAdmin() throws Exception {
        when(groupService.getGroupCourses(1)).thenReturn(List.of(getCoursesForTests().get(1)));
        when(courseService.getLastCoursesId()).thenReturn(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/group-courses?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Courses for group №" + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(1))));
    }

    @Test
    @WithMockRoleStaff
    void testFindGroupCoursesWithMockRoleStaff() throws Exception {
        when(groupService.getGroupCourses(1)).thenReturn(List.of(getCoursesForTests().get(1)));
        when(courseService.getLastCoursesId()).thenReturn(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/group-courses?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Courses for group №" + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(1))));
    }

    @Test
    @WithMockRoleLecturer
    void testFindGroupCoursesWithMockRoleLecturer() throws Exception {
        when(groupService.getGroupCourses(1)).thenReturn(List.of(getCoursesForTests().get(1)));
        when(courseService.getLastCoursesId()).thenReturn(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/group-courses?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Courses for group №" + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(1))));
    }

    @Test
    @WithMockRoleStudent
    void testFindGroupCoursesWithMockRoleStudent() throws Exception {
        when(groupService.getGroupCourses(1)).thenReturn(List.of(getCoursesForTests().get(1)));
        when(courseService.getLastCoursesId()).thenReturn(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/group-courses?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Courses for group №" + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses",
                        List.of(getCoursesForTests().get(1))));
    }

    @Test
    void testFindGroupCoursesWithAnonymous() throws Exception {
        when(groupService.getGroupCourses(1)).thenReturn(List.of(getCoursesForTests().get(1)));
        when(courseService.getLastCoursesId()).thenReturn(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/courses/group-courses?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }



    //	Data for tests
    private List<Course> getCoursesForTests() {
        List<Course> course = new LinkedList<>();
        course.add(new Course(1, "Test1", "Some Description"));
        course.add(new Course(2, "Test2", "Some Description"));
        course.add(new Course(3, "Test3", "Some Description"));
        return course;
    }
}