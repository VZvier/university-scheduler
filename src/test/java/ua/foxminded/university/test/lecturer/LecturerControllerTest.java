package ua.foxminded.university.test.lecturer;

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
import ua.foxminded.university.controller.LecturerController;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStudent;
import ua.foxminded.university.service.impl.CourseServiceImpl;
import ua.foxminded.university.service.impl.LecturerServiceImpl;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(LecturerController.class)
class LecturerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LecturerServiceImpl lecturerService;
    @MockBean
    private CourseServiceImpl courseService;


    @Test
    @WithMockRoleAdmin
    void testGetAllLecturersWithMockRoleAdmin() throws Exception {
        Mockito.when(this.lecturerService.getAllLecturers()).thenReturn(getLecturersForTest());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lecturers available at database."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", getLecturersForTest()));
    }

    @Test
    @WithMockRoleLecturer
    void testGetAllLecturersWithMockRoleLecturer() throws Exception {
        Mockito.when(this.lecturerService.getAllLecturers()).thenReturn(getLecturersForTest());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All lecturers available at database."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", getLecturersForTest()));
    }

    @Test
    @WithMockRoleStudent
    void testGetAllLecturersWithMockRoleStudent() throws Exception {
        Mockito.when(this.lecturerService.getAllLecturers()).thenReturn(getLecturersForTest());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/all"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testGetAllLecturersWithAnonymousUser() throws Exception {
        Mockito.when(this.lecturerService.getAllLecturers()).thenReturn(getLecturersForTest());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/all"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testGetLecturerByIdWithMockRoleAdmin() throws Exception {
        Mockito.when(this.lecturerService.getLecturer(1)).thenReturn(List.of(getLecturersForTest().get(0)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/?lecturerId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", List.of(getLecturersForTest().get(0))));
    }

    @Test
    @WithMockRoleLecturer
    void testGetLecturerByIdWithMockRoleLecturer() throws Exception {
        Mockito.when(this.lecturerService.getLecturer(1)).thenReturn(List.of(getLecturersForTest().get(0)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/?lecturerId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", List.of(getLecturersForTest().get(0))));
    }

    @Test
    @WithMockRoleStudent
    void testGetLecturerByIdWithMockRoleStudent() throws Exception {
        Mockito.when(this.lecturerService.getLecturer(1)).thenReturn(List.of(getLecturersForTest().get(0)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/?id=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testGetLecturerByIdWithAnonymousUser() throws Exception {
        Mockito.when(this.lecturerService.getLecturer(1)).thenReturn(List.of(getLecturersForTest().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturers/?id=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testGetCourseLecturerWithMockRoleAdmin() throws Exception {
        Mockito.when(this.courseService.getCourseLecturers(1)).thenReturn(getLecturersForTest());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/course-lecturers?courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturers who teach the course with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", getLecturersForTest()));
    }

    @Test
    @WithMockRoleLecturer
    void testGetCourseLecturerWithMockRoleLecturer() throws Exception {
        Mockito.when(this.courseService.getCourseLecturers(1)).thenReturn(getLecturersForTest());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/course-lecturers?courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturers who teach the course with id = " + 1 + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", getLecturersForTest()));
    }

    @Test
    @WithMockRoleStudent
    void testGetCourseLecturerWithMockRoleStudent() throws Exception {
        Mockito.when(this.courseService.getCourseLecturers(1)).thenReturn(getLecturersForTest());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lecturers/course-lecturers?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testGetCourseLecturerWithAnonymousUser() throws Exception {
        Mockito.when(this.courseService.getCourseLecturers(1)).thenReturn(getLecturersForTest());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/lecturers/course-lecturers?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    //	Data For Tests
    private List<Lecturer> getLecturersForTest() {
        List<Lecturer> lecturers = new LinkedList<>();
        lecturers.add(new Lecturer(1, "TestN1", "TestLN1", (new Course(1, "TestCourse1", "Course Description")), null));
        lecturers.add(new Lecturer(2, "TestN2", "TestLN2", (new Course(2, "TestCourse2", "Course Description")), null));
        lecturers.add(new Lecturer(3, "TestN3", "TestLN3", (new Course(3, "TestCourse3", "Course Description")), null));
        return lecturers;
    }
}