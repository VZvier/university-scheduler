package ua.foxminded.university.test.group;

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
import ua.foxminded.university.controller.GroupController;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStaff;
import ua.foxminded.university.mockuser.WithMockRoleStudent;
import ua.foxminded.university.service.impl.CourseServiceImpl;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.LectureServiceImpl;
import ua.foxminded.university.service.impl.StudentServiceImpl;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentServiceImpl studentService;
    @MockBean
    private GroupServiceImpl groupService;
    @MockBean
    private LectureServiceImpl lectureService;
    @MockBean
    private CourseServiceImpl courseService;

    private final List<Group> groups = getGroupForTest();


    @Test
    @WithMockRoleAdmin
    void testGetAllGroupsWithMockRoleAdmin() throws Exception {
        Mockito.when(groupService.getAllGroups()).thenReturn(groups);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All available groups from database."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", groups));
    }

    @Test
    @WithMockRoleLecturer
    void testGetAllGroupsWithMockRoleLecturer() throws Exception {
        Mockito.when(groupService.getAllGroups()).thenReturn(groups);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All available groups from database."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", groups));
    }

    @Test
    @WithMockRoleStudent
    void testGetAllGroupsWithMockRoleStudent() throws Exception {
        Mockito.when(groupService.getAllGroups()).thenReturn(groups);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "All available groups from database."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", groups));
    }

    @Test
    void testGetAllGroupsWithAnonymous() throws Exception {
        Mockito.when(groupService.getAllGroups()).thenReturn(groups);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/all"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testGetGroupByIdWithMockRoleAdmin() throws Exception {
        Mockito.when(groupService.getGroup(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group by id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    @WithMockRoleLecturer
    void testGetGroupByIdWithMockRoleLecturer() throws Exception {
        Mockito.when(groupService.getGroup(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group by id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    @WithMockRoleStudent
    void testGetGroupByIdWithMockRoleStudent() throws Exception {
        Mockito.when(groupService.getGroup(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group by id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    void testGetGroupByIdWithAnonymous() throws Exception {
        Mockito.when(groupService.getGroup(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testGetStudentsGroupWithMockRoleAdmin() throws Exception {
        Mockito.when(studentService.getStudentGroup(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/student-group?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group of student with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    @WithMockRoleLecturer
    void testGetStudentsGroupWithMockRoleLecturer() throws Exception {
        Mockito.when(studentService.getStudentGroup(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/student-group?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group of student with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    @WithMockRoleStudent
    void testGetStudentsGroupWithMockRoleStudent() throws Exception {
        Mockito.when(studentService.getStudentGroup(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/student-group?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group of student with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    void testGetStudentsGroupWithAnonymous() throws Exception {
        Mockito.when(studentService.getStudentGroup(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/student-group?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void testGetGroupsOnLectureWithMockRoleAdmin() throws Exception {
        Mockito.when(lectureService.getLectureGroups(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/lecture-groups?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Groups of lecture with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    @WithMockRoleStaff
    void testGetGroupsOnLectureWithMockRoleStaff() throws Exception {
        Mockito.when(lectureService.getLectureGroups(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/lecture-groups?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Groups of lecture with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    @WithMockRoleLecturer
    void testGetGroupsOnLectureWithMockRoleLecturer() throws Exception {
        Mockito.when(lectureService.getLectureGroups(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/common/groups/lecture-groups?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Groups of lecture with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    @WithMockRoleStudent
    void testGetGroupsOnLectureWithMockRoleStudent() throws Exception {
        Mockito.when(lectureService.getLectureGroups(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/common/groups/lecture-groups?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Groups of lecture with id = " + 1 + "."))

                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(0))));
    }

    @Test
    void testGetGroupsOnLectureWithAnonymous() throws Exception {
        Mockito.when(lectureService.getLectureGroups(1)).thenReturn(List.of(groups.get(0)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/common/groups/lecture-groups?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }


    @Test
    @WithMockRoleAdmin
    void testGetGroupsOnCourseWithMockRoleAdmin() throws Exception {
        Mockito.when(courseService.getGroupsOnCourse(1)).thenReturn(List.of(groups.get(1)));
        Mockito.when(groupService.getLastGroupId()).thenReturn(1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/common/groups/course-groups?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                                String.format("Groups on course №%s.", 1)))

                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(1))));
    }


    @Test
    @WithMockRoleStaff
    void testGetGroupsOnCourseWithMockRoleStaff() throws Exception {
        Mockito.when(courseService.getGroupsOnCourse(1)).thenReturn(List.of(groups.get(1)));
        Mockito.when(groupService.getLastGroupId()).thenReturn(1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/common/groups/course-groups?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Groups on course №%s.", 1)))

                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(1))));
    }

    @Test
    @WithMockRoleLecturer
    void testGetGroupsOnCourseWithMockRoleLecturer() throws Exception {
        Mockito.when(courseService.getGroupsOnCourse(1)).thenReturn(List.of(groups.get(1)));
        Mockito.when(groupService.getLastGroupId()).thenReturn(1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/common/groups/course-groups?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Groups on course №%s.", 1)))

                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(1))));
    }

    @Test
    @WithMockRoleStudent
    void testGetGroupsOnCourseWithMockRoleStudent() throws Exception {
        Mockito.when(courseService.getGroupsOnCourse(1)).thenReturn(List.of(groups.get(1)));
        Mockito.when(groupService.getLastGroupId()).thenReturn(1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/common/groups/course-groups?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Groups on course №%s.", 1)))

                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(groups.get(1))));
    }

    @Test
    void testGetGroupsOnCourseWithAnonymous() throws Exception {
        Mockito.when(courseService.getGroupsOnCourse(1)).thenReturn(List.of(groups.get(1)));
        Mockito.when(groupService.getLastGroupId()).thenReturn(1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/common/groups/course-groups?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    //	Data for tests
    private List<Group> getGroupForTest() {
        List<Group> groups = new LinkedList<>();
        groups.add(new Group(1, "XX-11", new LinkedList<Student>(),
                new LinkedList<Course>(), new LinkedList<Lecture>()));
        groups.add(new Group(2, "XX-12", new LinkedList<Student>(),
                new LinkedList<Course>(), new LinkedList<Lecture>()));
        return groups;
    }
}