package ua.foxminded.university.test.staff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.configuration.DefaultSecurityConfig;
import ua.foxminded.university.controller.StaffController;
import ua.foxminded.university.entity.*;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleStaff;
import ua.foxminded.university.mockuser.WithMockRolesStudentAndLecturer;
import ua.foxminded.university.service.*;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StaffController.class)
@Import(DefaultSecurityConfig.class)
class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private LecturerService lecturerService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private LectureService lectureService;

    @MockBean
    private StaffService staffService;

    @Test
    @WithMockRoleStaff
    void testGetStudentUpdatePageWithRoleStaff() throws Exception {
        int lastGroupNumber = 1;

        when(groupService.getLastGroupId()).thenReturn(lastGroupNumber);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/updating-student?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Student Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update first name, last name, or change group by id till "
                                + lastGroupNumber + "!\nFor remove group set groupId = 0"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attribute("student", getStudents().get(0)));
    }

    @Test
    @WithMockRoleAdmin
    void testGetStudentUpdatePageWithRoleAdmin() throws Exception {
        int lastGroupNumber = 1;

        when(groupService.getLastGroupId()).thenReturn(lastGroupNumber);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/updating-student?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Student Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update first name, last name, or change group by id till "
                                + lastGroupNumber + "!\nFor remove group set groupId = 0"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attribute("student", getStudents().get(0)));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testGetStudentUpdatePageWithRolesStudentAndLecturer() throws Exception {
        int lastGroupNumber = 1;

        when(groupService.getLastGroupId()).thenReturn(lastGroupNumber);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/updating-student?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testGetLecturerUpdatePageWithMockRoleStaff() throws Exception {
        Lecturer lecturer = getLecturers().get(0);

        when(courseService.getAllCourses()).thenReturn(getCourses());
        when(lecturerService.getLecturer(1)).thenReturn(List.of(lecturer));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/lecturers/updating-lecturer?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Lecturer Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update first name, last name, or change subject!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lecturer"))
                .andExpect(MockMvcResultMatchers.model().attribute("lecturer", lecturer));
    }

    @Test
    @WithMockRoleAdmin
    void testGetLecturerUpdatePageWithMockRoleAdmin() throws Exception {
        Lecturer lecturer = getLecturers().get(0);

        when(courseService.getAllCourses()).thenReturn(getCourses());
        when(lecturerService.getLecturer(1)).thenReturn(List.of(lecturer));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/lecturers/updating-lecturer?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Lecturer Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update first name, last name, or change subject!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lecturer"))
                .andExpect(MockMvcResultMatchers.model().attribute("lecturer", lecturer));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testGetLecturerUpdatePageWithMockRolesStudentAndLecturer() throws Exception {
        Lecturer lecturer = getLecturers().get(0);

        when(courseService.getAllCourses()).thenReturn(getCourses());
        when(lecturerService.getLecturer(1)).thenReturn(List.of(lecturer));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/lecturers/updating-lecturer?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testGetCourseUpdatePageWithMockRoleStaff() throws Exception {
        Course course = getCourses().get(0);

        when(courseService.getCourseById(1)).thenReturn(List.of(course));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/courses/updating-course?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Course Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update course name or description!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.model().attribute("course", course));
    }

    @Test
    @WithMockRoleAdmin
    void testGetCourseUpdatePageWithMockRoleStaffWithMockRoleAdmin() throws Exception {
        Course course = getCourses().get(0);

        when(courseService.getCourseById(1)).thenReturn(List.of(course));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/courses/updating-course?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Course Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update course name or description!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.model().attribute("course", course));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testGetCourseUpdatePageWithMockRolesStudentAndLecturer() throws Exception {
        Course course = getCourses().get(0);

        when(courseService.getCourseById(1)).thenReturn(List.of(course));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/courses/updating-course?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testGetGroupUpdatePageWithMockRoleStaff() throws Exception {
        Group group = getGroups().get(0);

        when(groupService.getGroup(1)).thenReturn(List.of(group));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/updating-group?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Group Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update group name!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.model().attribute("group", group));

    }

    @Test
    @WithMockRoleAdmin
    void testGetGroupUpdatePageWithMockRoleAdmin() throws Exception {
        Group group = getGroups().get(0);

        when(groupService.getGroup(1)).thenReturn(List.of(group));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/updating-group?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Group Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update group name!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.model().attribute("group", group));

    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testGetGroupUpdatePageWithMockRolesStudentAndLecturer() throws Exception {
        Group group = getGroups().get(0);

        when(groupService.getGroup(1)).thenReturn(List.of(group));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/updating-group?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockRoleStaff
    void testGetLectureUpdatePageWithMockRoleStaff() throws Exception {
        Lecture lecture = getLectures().get(0);
        lecture.setGroups(List.of(getGroups().get(0)));
        lecture.setCourse(getCourses().get(0));

        when(lectureService.getLecture(1)).thenReturn(List.of(lecture));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/lectures/updating-lecture?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Lecture Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update lecture №" + 1 + " by changing day, pair, course or adding group!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lecture"))
                .andExpect(MockMvcResultMatchers.model().attribute("lecture", lecture));
    }

    @Test
    @WithMockRoleAdmin
    void testGetLectureUpdatePageWithMockRoleAdmin() throws Exception {
        Lecture lecture = getLectures().get(0);
        lecture.setGroups(List.of(getGroups().get(0)));
        lecture.setCourse(getCourses().get(0));

        when(lectureService.getLecture(1)).thenReturn(List.of(lecture));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/lectures/updating-lecture?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Update Lecture Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "You can update lecture №" + 1 + " by changing day, pair, course or adding group!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("action"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lecture"))
                .andExpect(MockMvcResultMatchers.model().attribute("lecture", lecture));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testGetLectureUpdatePageWithMockRolesStudentAndLecturer() throws Exception {
        Lecture lecture = getLectures().get(0);
        lecture.setGroups(List.of(getGroups().get(0)));
        lecture.setCourse(getCourses().get(0));

        when(lectureService.getLecture(1)).thenReturn(List.of(lecture));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/lectures/updating-lecture?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAddNewStudentWithMockRoleStaff() throws Exception {
        Student student = getStudents().get(1);
        student.setGroup(getGroups().get(1));

        when(studentService.saveUpdateStudent(student)).thenReturn(student);
        when(studentService.getLastStudentId()).thenReturn(1L);
        when(studentService.getStudentsInGroup(1)).thenReturn(getStudents());
        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/students/new-student" +
                        "?studentId=1&firstName=TestStudent1&lastName=TestStudent1&groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Student saved %s, %s, %s, %s!", student.getId(), student.getFirstName(),
                                student.getLastName(), student.getGroup())))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", getStudents()));
    }

    @Test
    @WithMockRoleAdmin
    void testAddNewStudentWithMockRoleAdmin() throws Exception {
        Student student = getStudents().get(1);
        student.setGroup(getGroups().get(1));

        when(studentService.saveUpdateStudent(student)).thenReturn(student);
        when(studentService.getLastStudentId()).thenReturn(1L);
        when(studentService.getStudentsInGroup(1)).thenReturn(getStudents());
        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/students/new-student" +
                        "?studentId=1&firstName=TestStudent1&lastName=TestStudent1&groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Student saved %s, %s, %s, %s!", student.getId(), student.getFirstName(),
                                student.getLastName(), student.getGroup())))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", getStudents()));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAddNewStudentWithMockRolesStudentAndLecturer() throws Exception {
        Student student = getStudents().get(1);
        student.setGroup(getGroups().get(1));

        when(studentService.saveUpdateStudent(student)).thenReturn(student);
        when(studentService.getLastStudentId()).thenReturn(1L);
        when(studentService.getStudentsInGroup(1)).thenReturn(getStudents());
        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/students/new-student" +
                        "?studentId=1&firstName=TestStudent1&lastName=TestStudent1&groupId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAddNewLecturerWithMockRoleStaff() throws Exception {
        Lecturer lecturer = getLecturers().get(1);
        lecturer.setCourse(getCourses().get(1));

        when(lecturerService.saveUpdateLecturer(lecturer)).thenReturn(lecturer);
        when(lecturerService.getLastLecturerId()).thenReturn(1);
        when(lecturerService.getAllLecturers()).thenReturn(getLecturers());
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/new-lecturer" +
                        "?fName=TestLecturer1&lName=TestLecturer1&course=TestCourse1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Lecturer saved %s %s!", "TestLecturer1", "TestLecturer1")))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", getLecturers()));
    }

    @Test
    @WithMockRoleAdmin
    void testAddNewLecturerWithMockRoleAdmin() throws Exception {
        Lecturer lecturer = getLecturers().get(1);
        lecturer.setCourse(getCourses().get(1));

        when(lecturerService.saveUpdateLecturer(lecturer)).thenReturn(lecturer);
        when(lecturerService.getLastLecturerId()).thenReturn(1);
        when(lecturerService.getAllLecturers()).thenReturn(getLecturers());
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/new-lecturer" +
                        "?fName=TestLecturer1&lName=TestLecturer1&course=TestCourse1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Lecturer saved %s %s!", "TestLecturer1", "TestLecturer1")))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", getLecturers()));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAddNewLecturerWithMockRolesStudentAndLecturer() throws Exception {
        Lecturer lecturer = getLecturers().get(1);
        lecturer.setCourse(getCourses().get(1));

        when(lecturerService.saveUpdateLecturer(lecturer)).thenReturn(lecturer);
        when(lecturerService.getLastLecturerId()).thenReturn(1);
        when(lecturerService.getAllLecturers()).thenReturn(getLecturers());
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/new-lecturer" +
                        "?fName=TestLecturer1&lName=TestLecturer1&course=TestCourse1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAddNewGroupWithMockRoleStaff() throws Exception {
        Group group = getGroups().get(1);
        group.setCourses(new LinkedList<>());

        doNothing().when(groupService).saveUpdateGroup(group);
        when(groupService.getLastGroupId()).thenReturn(1);
        when(groupService.getAllGroups()).thenReturn(List.of(group));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/groups/new-group" +
                        "?groupId=1&name=TT-11"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Group saved %s!", group.getName())))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(group)));
    }

    @Test
    @WithMockRoleAdmin
    void testAddNewGroupWithMockRoleAdmin() throws Exception {
        Group group = getGroups().get(1);
        group.setCourses(new LinkedList<>());

        doNothing().when(groupService).saveUpdateGroup(group);
        when(groupService.getLastGroupId()).thenReturn(1);
        when(groupService.getAllGroups()).thenReturn(List.of(group));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/groups/new-group" +
                        "?groupId=1&name=TT-11"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Group saved %s!", group.getName())))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(group)));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAddNewGroupWithMockRolesStudentAndLecturer() throws Exception {
        Group group = getGroups().get(1);
        group.setCourses(new LinkedList<>());

        doNothing().when(groupService).saveUpdateGroup(group);
        when(groupService.getLastGroupId()).thenReturn(1);
        when(groupService.getAllGroups()).thenReturn(List.of(group));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/groups/new-group" +
                        "?groupId=1&name=TT-11"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAddNewCourseWithMockRoleStaff() throws Exception {
        Course course = getCourses().get(1);
        course.setGroups(new LinkedList<>());

        when(courseService.saveUpdateCourses(course)).thenReturn(course);
        when(courseService.getLastCoursesId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/new-course" +
                        "?courseId=1&name=TestCourse1&description=TestDescription1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Course %s!", "saved" + "TestCourse1")))
                .andExpect(MockMvcResultMatchers.model().attributeExists("idForNewCourse"))
                .andExpect(MockMvcResultMatchers.model().attribute("idForNewCourse", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", List.of(course)));
    }

    @Test
    @WithMockRoleAdmin
    void testAddNewCourseWithMockRoleAdmin() throws Exception {
        Course course = getCourses().get(1);
        course.setGroups(new LinkedList<>());

        when(courseService.saveUpdateCourses(course)).thenReturn(course);
        when(courseService.getLastCoursesId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/new-course" +
                        "?courseId=1&name=TestCourse1&description=TestDescription1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        String.format("Course %s!", "saved" + "TestCourse1")))
                .andExpect(MockMvcResultMatchers.model().attributeExists("idForNewCourse"))
                .andExpect(MockMvcResultMatchers.model().attribute("idForNewCourse", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", List.of(course)));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAddNewCourseWithMockRolesStudentAndLecturer() throws Exception {
        Course course = getCourses().get(1);
        course.setGroups(new LinkedList<>());

        when(courseService.saveUpdateCourses(course)).thenReturn(course);
        when(courseService.getLastCoursesId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/new-course" +
                        "?courseId=1&name=TestCourse1&description=TestDescription1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAddNewLectureWithMockRoleStaff() throws Exception {
        Lecture l = getLectures().get(1);
        l.setGroups(getGroups());
        l.setCourse(getCourses().get(1));
        List<Lecture> lecture = List.of(l);

        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));
        when(groupService.getGroup(2)).thenReturn(List.of(getGroups().get(1)));
        when(groupService.getGroup(3)).thenReturn(List.of(getGroups().get(2)));
        when(lectureService.saveUpdateLecture(l)).thenReturn(List.of(l));
        when(courseService.getCourseByName("TestCourse1")).thenReturn(l.getCourse());
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/new-lecture" +
                        "?lectureId=1&day=1&pair=2&course=TestCourse1&gIdOne=1&gIdTwo=2&gIdThree=3"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Saved new Lecture with id= " + lecture.get(0).getId() + "!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newLectureId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newLectureId", 1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", List.of(l)));
    }

    @Test
    @WithMockRoleAdmin
    void testAddNewLectureWithMockRoleAdmin() throws Exception {
        Lecture l = getLectures().get(1);
        l.setGroups(getGroups());
        l.setCourse(getCourses().get(1));
        List<Lecture> lecture = List.of(l);

        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));
        when(groupService.getGroup(2)).thenReturn(List.of(getGroups().get(1)));
        when(groupService.getGroup(3)).thenReturn(List.of(getGroups().get(2)));
        when(lectureService.saveUpdateLecture(l)).thenReturn(List.of(l));
        when(courseService.getCourseByName("TestCourse1")).thenReturn(l.getCourse());
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/new-lecture" +
                        "?lectureId=1&day=1&pair=2&course=TestCourse1&gIdOne=1&gIdTwo=2&gIdThree=3"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Saved new Lecture with id= " + lecture.get(0).getId() + "!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newLectureId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newLectureId", 1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", List.of(l)));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAddNewLectureWithMockRolesStudentAndLecturer() throws Exception {
        Lecture l = getLectures().get(1);
        l.setGroups(getGroups());
        l.setCourse(getCourses().get(1));

        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));
        when(groupService.getGroup(2)).thenReturn(List.of(getGroups().get(1)));
        when(groupService.getGroup(3)).thenReturn(List.of(getGroups().get(2)));
        when(lectureService.saveUpdateLecture(l)).thenReturn(List.of(l));
        when(courseService.getCourseByName("TestCourse1")).thenReturn(l.getCourse());
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/new-lecture" +
                        "?lectureId=1&day=1&pair=2&course=TestCourse1&gIdOne=1&gIdTwo=2&gIdThree=3"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testRemoveStudentWithMockRoleStaff() throws Exception {
        doNothing().when(studentService).removeStudent(1);
        when(studentService.getAllStudents()).thenReturn(getStudents());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/students/removing-student?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Student with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", getStudents()));
    }

    @Test
    @WithMockRoleAdmin
    void testRemoveStudentWithMockRoleAdmin() throws Exception {
        doNothing().when(studentService).removeStudent(1);
        when(studentService.getAllStudents()).thenReturn(getStudents());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/students/removing-student?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Student with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", getStudents()));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testRemoveStudentWithMockRolesStudentAndLecturer() throws Exception {
        doNothing().when(studentService).removeStudent(1);
        when(studentService.getAllStudents()).thenReturn(getStudents());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/students/removing-student?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testRemoveLecturerWithMockRoleStaff() throws Exception {
        doNothing().when(lecturerService).removeLecturer(1);
        when(courseService.getAllCourses()).thenReturn(getCourses());
        when(lecturerService.getAllLecturers()).thenReturn(getLecturers());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/removing-lecturer?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", getLecturers()));
    }

    @Test
    @WithMockRoleAdmin
    void testRemoveLecturerWithMockRoleAdmin() throws Exception {
        doNothing().when(lecturerService).removeLecturer(1);
        when(courseService.getAllCourses()).thenReturn(getCourses());
        when(lecturerService.getAllLecturers()).thenReturn(getLecturers());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/removing-lecturer?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", getLecturers()));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testRemoveLecturerWithMockRolesStudentAndLecturer() throws Exception {
        doNothing().when(lecturerService).removeLecturer(1);
        when(courseService.getAllCourses()).thenReturn(getCourses());
        when(lecturerService.getAllLecturers()).thenReturn(getLecturers());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/removing-lecturer?lecturerId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testRemoveCourseWithMockRoleStaff() throws Exception {
        Course course = getCourses().get(0);
        course.setLecturers(getLecturers());
        course.setLectures(getLectures());

        when(courseService.getCourseById(1)).thenReturn(List.of(course));
        doNothing().when(lectureService).removeLecture(1);
        doNothing().when(lecturerService).removeLecturerFromCourse(1);
        doNothing().when(groupService).removeGroupFromCourse(1, 1);
        doNothing().when(courseService).removeCourse(1);
        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/removing-course?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", List.of(course)));
    }

    @Test
    @WithMockRoleAdmin
    void testRemoveCourseWithMockRoleAdmin() throws Exception {
        Course course = getCourses().get(0);
        course.setLecturers(getLecturers());
        course.setLectures(getLectures());
        when(courseService.getCourseById(1)).thenReturn(List.of(course));
        doNothing().when(lectureService).removeLecture(1);
        doNothing().when(lecturerService).removeLecturerFromCourse(1);
        doNothing().when(groupService).removeGroupFromCourse(1, 1);
        doNothing().when(courseService).removeCourse(1);
        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/removing-course?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", List.of(course)));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testRemoveCourseWithMockRolesStudentAndLecturer() throws Exception {
        doNothing().when(courseService).removeCourse(1);
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/removing-course?courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testRemoveGroupWithMockRoleStaff() throws Exception {
        List<Group> groupList = getGroups();
        for (Group group : groupList) {
            group.setCourses(getCourses());
        }

        doNothing().when(groupService).removeGroup(1);
        when(groupService.getLastGroupId()).thenReturn(0);
        when(groupService.getAllGroups()).thenReturn(groupList);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/groups/removing-group?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", groupList));
    }

    @Test
    @WithMockRoleAdmin
    void testRemoveGroupWithMockRoleAdmin() throws Exception {
        List<Group> groupList = getGroups();
        for (Group group : groupList) {
            group.setCourses(getCourses());
        }

        doNothing().when(groupService).removeGroup(1);
        when(groupService.getLastGroupId()).thenReturn(0);
        when(groupService.getAllGroups()).thenReturn(groupList);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/groups/removing-group?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Group Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", groupList));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testRemoveGroupWithMockRolesStudentAndLecturer() throws Exception {
        List<Group> groupList = getGroups();
        for (Group group : groupList) {
            group.setCourses(getCourses());
        }

        doNothing().when(groupService).removeGroup(1);
        when(groupService.getLastGroupId()).thenReturn(0);
        when(groupService.getAllGroups()).thenReturn(groupList);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/groups/removing-group?groupId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testRemoveLectureWithMockRoleStaff() throws Exception {
        List<Lecture> lectures = getLectures().subList(0, 2);
        for (Lecture lecture : lectures) {
            lecture.setGroups(getGroups());
            lecture.setCourse(getCourses().get(1));
        }

        doNothing().when(lectureService).removeLecture(1);
        when(lectureService.getAllLectures()).thenReturn(lectures);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/removing-lecture?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    @WithMockRoleAdmin
    void testRemoveLectureWithMockRoleAdmin() throws Exception {
        List<Lecture> lectures = getLectures().subList(0, 2);
        for (Lecture lecture : lectures) {
            lecture.setGroups(getGroups());
            lecture.setCourse(getCourses().get(1));
        }

        doNothing().when(lectureService).removeLecture(1);
        when(lectureService.getAllLectures()).thenReturn(lectures);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/removing-lecture?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture with id=" + 1 + " was deleted!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", lectures));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testRemoveLectureWithMockRolesStudentAndLecturer() throws Exception {
        List<Lecture> lectures = getLectures().subList(0, 2);
        for (Lecture lecture : lectures) {
            lecture.setGroups(getGroups());
            lecture.setCourse(getCourses().get(1));
        }

        doNothing().when(lectureService).removeLecture(1);
        when(lectureService.getAllLectures()).thenReturn(lectures);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/removing-lecture?lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAssignGroupToCourseWithMockRoleStaff() throws Exception {
        when(staffService.assignGroupToCourse(1, 1)).thenReturn(getCourses());

        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?groupId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group №" + 1 + " assigned to Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));

    }

    @Test
    @WithMockRoleAdmin
    void testAssignGroupToCourseWithMockRoleAdmin() throws Exception {
        when(staffService.assignGroupToCourse(1, 1)).thenReturn(getCourses());

        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?groupId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group №" + 1 + " assigned to Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));

    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAssignGroupToCourseWithMockRolesStudentAndLecturer() throws Exception {
        when(staffService.assignGroupToCourse(1, 1)).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?groupId=1&courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockRoleStaff
    void testAssignLecturerToCourseWithMockRoleStaff() throws Exception {

        when(staffService.assignLecturerToCourse(1, 1)).thenReturn(getCourses());

        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?lecturerId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer №" + 1 + " assigned to Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));
    }

    @Test
    @WithMockRoleAdmin
    void testAssignLecturerToCourseWithMockRoleAdmin() throws Exception {

        when(staffService.assignLecturerToCourse(1, 1)).thenReturn(getCourses());

        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?lecturerId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer №" + 1 + " assigned to Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAssignLecturerToCourseWithMockRolesStudentAndLecturer() throws Exception {

        when(staffService.assignLecturerToCourse(1, 1)).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?lecturerId=1&courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAssignLectureToCourseWithMockRoleStaff() throws Exception {
        when(staffService.assignLectureByIdToCourse(1, 1)).thenReturn(getCourses());


        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?lectureId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture №" + 1 + " assigned to Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));
    }

    @Test
    @WithMockRoleAdmin
    void testAssignLectureToCourseWithMockRoleAdmin() throws Exception {
        when(staffService.assignLectureByIdToCourse(1, 1)).thenReturn(getCourses());


        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?lectureId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture №" + 1 + " assigned to Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAssignLectureToCourseWithMockRolesStudentAndLecturer() throws Exception {
        when(staffService.assignLectureByIdToCourse(1, 1)).thenReturn(getCourses());


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/courses/assigning/?lectureId=1&courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testUnAssignGroupFromCourseWithMockRoleStaff() throws Exception {
        when(staffService.unsignGroupFromCourse(1, 1)).thenReturn(getCourses());


        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/unsigning/?groupId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group № " + 1 + " unassigned from Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));
    }

    @Test
    @WithMockRoleAdmin
    void testUnAssignGroupFromCourseWithMockRoleAdmin() throws Exception {
        when(staffService.unsignGroupFromCourse(1, 1)).thenReturn(getCourses());


        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/unsigning/?groupId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group № " + 1 + " unassigned from Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testUnAssignGroupFromCourseWithMockRolesStudentAndLecturer() throws Exception {
        when(staffService.unsignGroupFromCourse(1, 1)).thenReturn(getCourses());


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/courses/unsigning/?groupId=1&courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testUnAssignLecturerFromCourseWithMockRoleStaff() throws Exception {
        when(staffService.unsignLecturerFromCourse(1)).thenReturn(getCourses());


        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/unsigning/?lecturerId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer № " + 1 + " unassigned from Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));
    }

    @Test
    @WithMockRoleAdmin
    void testUnAssignLecturerFromCourseWithMockRoleAdmin() throws Exception {
        when(staffService.unsignLecturerFromCourse(1)).thenReturn(getCourses());


        mockMvc.perform(MockMvcRequestBuilders.get("/staff/editing/courses/unsigning/?lecturerId=1&courseId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Courses Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer № " + 1 + " unassigned from Course"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testUnAssignLecturerFromCourseWithAnyRoleExceptStaff() throws Exception {
        when(staffService.unsignLecturerFromCourse(1)).thenReturn(getCourses());


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/courses/unsigning/?lecturerId=1&courseId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAssignGroupToLectureWithMockRoleStaff() throws Exception {
        when(lectureService.addGroupToLecture(1, 1)).thenReturn(List.of(getOneLecture()));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/adding-lecture?groupId=1&lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group " + 1 + " was added to lecture " + 1 + "!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", List.of(getOneLecture())));
    }

    @Test
    @WithMockRoleAdmin
    void testAssignGroupToLectureWithMockRoleAdmin() throws Exception {
        when(lectureService.addGroupToLecture(1, 1)).thenReturn(List.of(getOneLecture()));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/adding-lecture?groupId=1&lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group " + 1 + " was added to lecture " + 1 + "!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", List.of(getOneLecture())));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAssignGroupToLectureWithMockAnyRoleExceptStaff() throws Exception {
        when(lectureService.addGroupToLecture(1, 1)).thenReturn(List.of(getOneLecture()));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/adding-lecture?groupId=1&lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testAssignStudentToGroupWithMockRoleStaff() throws Exception {
        doNothing().when(studentService).addStudentToGroup(1, 1);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(1)));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/adding-group?groupId=1&studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Student " + 1 + " was added to group " + 1 + "!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", List.of(getStudents().get(1))));
    }

    @Test
    @WithMockRoleAdmin
    void testAssignStudentToGroupWithMockRoleAdmin() throws Exception {
        doNothing().when(studentService).addStudentToGroup(1, 1);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(1)));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/adding-group?groupId=1&studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Student " + 1 + " was added to group " + 1 + "!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", List.of(getStudents().get(1))));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testAssignStudentToGroupWithMockRolesStudentAndLecturer() throws Exception {
        doNothing().when(studentService).addStudentToGroup(1, 1);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(1)));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/adding-group?groupId=1&studentId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testUnAssignGroupFromLectureWithMockRoleStaff() throws Exception {
        doNothing().when(groupService).removeGroupFromLecture(1, 1);
        when(lectureService.getLecture(1)).thenReturn(List.of(getOneLecture()));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/removing-lecture?groupId=1&lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group " + 1 + " was deleted from lecture " + 1 + "!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", List.of(getOneLecture())));
    }

    @Test
    @WithMockRoleAdmin
    void testUnAssignGroupFromLectureWithMockRoleAdmin() throws Exception {
        doNothing().when(groupService).removeGroupFromLecture(1, 1);
        when(lectureService.getLecture(1)).thenReturn(List.of(getOneLecture()));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/removing-lecture?groupId=1&lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group " + 1 + " was deleted from lecture " + 1 + "!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", List.of(getOneLecture())));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testUnAssignGroupFromLectureWithMockRolesStudentAndLecturer() throws Exception {
        doNothing().when(groupService).removeGroupFromLecture(1, 1);
        when(lectureService.getLecture(1)).thenReturn(List.of(getOneLecture()));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/groups/removing-lecture?groupId=1&lectureId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testUnAssignStudentFromGroupWithMockRoleStaff() throws Exception {
        doNothing().when(studentService).removeStudentFromGroup(1);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(1)));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/removing-group?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Student " + 1 + " was removed from his group!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", List.of(getStudents().get(1))));
    }

    @Test
    @WithMockRoleAdmin
    void testUnAssignStudentFromGroupWithMockRoleAdmin() throws Exception {
        doNothing().when(studentService).removeStudentFromGroup(1);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(1)));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/removing-group?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Student " + 1 + " was removed from his group!"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", List.of(getStudents().get(1))));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testUnAssignStudentFromGroupWithMockAnyRoleExceptStaff() throws Exception {
        doNothing().when(studentService).removeStudentFromGroup(1);
        when(studentService.getStudent(1)).thenReturn(List.of(getStudents().get(1)));


        mockMvc
                .perform(MockMvcRequestBuilders.get("/staff/editing/students/removing-group?studentId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testApplyStudentUpdateWithMockRoleStaff() throws Exception {
        Student student = getStudents().get(1);
        student.setGroup(getGroups().get(1));

        Mockito.when(studentService.saveUpdateStudent(student)).thenReturn(student);
        Mockito.when(studentService.getStudent(1)).thenReturn(List.of(student));
        Mockito.when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));

        mockMvc.perform(MockMvcRequestBuilders.post("/staff/editing/students/updating-student" +
                        "?studentId=1&firstName=TestStudent1&lastName=TestStudent1&groupId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Student was updated 1."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", List.of(student)));
    }

    @Test
    @WithMockRoleAdmin
    void testApplyStudentUpdateWithMockRoleAdmin() throws Exception {
        Student student = getStudents().get(1);
        student.setGroup(getGroups().get(1));

        Mockito.when(studentService.saveUpdateStudent(student)).thenReturn(student);
        Mockito.when(studentService.getStudent(1)).thenReturn(List.of(student));
        Mockito.when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));

        mockMvc.perform(MockMvcRequestBuilders.post("/staff/editing/students/updating-student" +
                        "?studentId=1&firstName=TestStudent1&lastName=TestStudent1&groupId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Student was updated 1."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attribute("listStudents", List.of(student)));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testApplyStudentUpdateWithMockAnyRoleExceptStaff() throws Exception {
        Student student = getStudents().get(1);
        student.setGroup(getGroups().get(1));

        Mockito.when(studentService.saveUpdateStudent(student)).thenReturn(student);
        Mockito.when(studentService.getStudent(1)).thenReturn(List.of(student));
        Mockito.when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/students/updating-student" +
                        "?studentId=1&firstName=TestStudent1&lastName=TestStudent1&groupId=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testApplyLecturerUpdateWithMockRoleStaff() throws Exception {
        Lecturer lecturer = getLecturers().get(1);
        lecturer.setCourse(getCourses().get(1));

        when(lecturerService.getLecturer(1)).thenReturn(List.of(lecturer));
        when(lecturerService.getAllLecturers()).thenReturn(List.of(lecturer));
        when(courseService.getCourseByName(lecturer.getCourse().getName())).thenReturn(getCourses().get(1));
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc.perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/updating-lecturer" +
                        "?lecturerId=1&firstName=TestLecturer1&lastName=TestLecturer1&course=TestCourse1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer was updated " + lecturer + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", List.of(lecturer)));
    }

    @Test
    @WithMockRoleAdmin
    void testApplyLecturerUpdateWithMockRoleAdmin() throws Exception {
        Lecturer lecturer = getLecturers().get(1);
        lecturer.setCourse(getCourses().get(1));

        when(lecturerService.getLecturer(1)).thenReturn(List.of(lecturer));
        when(lecturerService.getAllLecturers()).thenReturn(List.of(lecturer));
        when(courseService.getCourseByName(lecturer.getCourse().getName())).thenReturn(getCourses().get(1));
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc.perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/updating-lecturer" +
                        "?lecturerId=1&firstName=TestLecturer1&lastName=TestLecturer1&course=TestCourse1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lecturers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecturer Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecturer was updated " + lecturer + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", getCourses()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLecturers"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLecturers", List.of(lecturer)));
    }

    @Test
    @WithMockRolesStudentAndLecturer
    void testApplyLecturerUpdateWithMockAnyRoleExceptStaff() throws Exception {
        Lecturer lecturer = getLecturers().get(1);
        lecturer.setCourse(getCourses().get(1));

        when(lecturerService.getLecturer(1)).thenReturn(List.of(lecturer));
        when(lecturerService.getAllLecturers()).thenReturn(List.of(lecturer));
        when(courseService.getCourseByName(lecturer.getCourse().getName())).thenReturn(getCourses().get(1));
        when(courseService.getAllCourses()).thenReturn(getCourses());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lecturers/updating-lecturer" +
                        "?lecturerId=1&firstName=TestLecturer1&lastName=TestLecturer1&course=TestCourse1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testApplyCourseUpdateWithMockRoleStaff() throws Exception {
        Course course = getCourses().get(1);
        course.setGroups(getGroups());

        when(courseService.getCourseById(1)).thenReturn(List.of(course));
        when(courseService.saveUpdateCourses(course)).thenReturn(course);
        when(courseService.getLastCoursesId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/updating-course" +
                        "?courseId=1&name=TestCourse1&description=TestDescription1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course was updated " + course + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("idForNewCourse"))
                .andExpect(MockMvcResultMatchers.model().attribute("idForNewCourse", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", List.of(course)));
    }

    @Test
    @WithMockRoleAdmin
    void testApplyCourseUpdateWithMockRoleAdmin() throws Exception {
        Course course = getCourses().get(1);
        course.setGroups(getGroups());

        when(courseService.getCourseById(1)).thenReturn(List.of(course));
        when(courseService.saveUpdateCourses(course)).thenReturn(course);
        when(courseService.getLastCoursesId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/updating-course" +
                        "?courseId=1&name=TestCourse1&description=TestDescription1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Course Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Course was updated " + course + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("idForNewCourse"))
                .andExpect(MockMvcResultMatchers.model().attribute("idForNewCourse", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", List.of(course)));
    }


    @Test
    @WithMockRolesStudentAndLecturer
    void testApplyCourseUpdateWithMockRolesStudentAndLecturer() throws Exception {
        Course course = getCourses().get(1);
        course.setGroups(getGroups());

        when(courseService.getCourseById(1)).thenReturn(List.of(course));
        when(courseService.saveUpdateCourses(course)).thenReturn(course);
        when(courseService.getLastCoursesId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/courses/updating-course" +
                        "?courseId=1&name=TestCourse1&description=TestDescription1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testApplyGroupUpdateWithMockRoleStaff() throws Exception {
        Group group = getGroups().get(1);
        group.setCourses(getCourses());

        when(groupService.getGroup(1)).thenReturn(List.of(group));
        doNothing().when(groupService).saveUpdateGroup(group);
        when(groupService.getLastGroupId()).thenReturn(1);
        when(groupService.getAllGroups()).thenReturn(List.of(group));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/group/updating-group" +
                        "?groupId=1&name=TT-11"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Groups Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group was updated " + group + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(group)));
    }

    @Test
    @WithMockRoleAdmin
    void testApplyGroupUpdateWithMockRoleAdmin() throws Exception {
        Group group = getGroups().get(1);
        group.setCourses(getCourses());

        when(groupService.getGroup(1)).thenReturn(List.of(group));
        doNothing().when(groupService).saveUpdateGroup(group);
        when(groupService.getLastGroupId()).thenReturn(1);
        when(groupService.getAllGroups()).thenReturn(List.of(group));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/group/updating-group" +
                        "?groupId=1&name=TT-11"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/groups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Groups Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Group was updated " + group + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newGroupId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newGroupId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listGroups"))
                .andExpect(MockMvcResultMatchers.model().attribute("listGroups", List.of(group)));
    }


    @Test
    @WithMockRolesStudentAndLecturer
    void testApplyGroupUpdateWithMockRolesStudentAndLecturer() throws Exception {
        Group group = getGroups().get(1);
        group.setCourses(getCourses());

        when(groupService.getGroup(1)).thenReturn(List.of(group));
        doNothing().when(groupService).saveUpdateGroup(group);
        when(groupService.getLastGroupId()).thenReturn(1);
        when(groupService.getAllGroups()).thenReturn(List.of(group));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/group/updating-group" +
                        "?groupId=1&name=TT-11"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStaff
    void testApplyLectureUpdateWithMockRoleStaff() throws Exception {
        Lecture lecture = getOneLecture();
        lecture.setGroups(getGroups());

        when(lectureService.getLecture(1)).thenReturn(List.of(lecture));
        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));
        when(groupService.getGroup(2)).thenReturn(List.of(getGroups().get(1)));
        when(groupService.getGroup(3)).thenReturn(List.of(getGroups().get(2)));
        when(courseService.getCourseByName(lecture.getCourse().getName())).thenReturn(lecture.getCourse());
        when(lectureService.saveUpdateLecture(lecture)).thenReturn(List.of(lecture));
        when(lectureService.getLastLectureId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(lecture.getCourse()));
        when(lectureService.getAllLectures()).thenReturn(List.of(lecture));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/updating-lecture" +
                        "?lectureId=1&day=1&pair=1&course=TestCourse1&gIdOne=1&gIdTwo=2&gIdThree=3"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture was updated " + lecture.getId() + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newLectureId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newLectureId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", List.of(lecture.getCourse())))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", List.of(lecture)));
    }

    @Test
    @WithMockRoleAdmin
    void testApplyLectureUpdateWithMockRoleAdmin() throws Exception {
        Lecture lecture = getOneLecture();
        lecture.setGroups(getGroups());

        when(lectureService.getLecture(1)).thenReturn(List.of(lecture));
        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));
        when(groupService.getGroup(2)).thenReturn(List.of(getGroups().get(1)));
        when(groupService.getGroup(3)).thenReturn(List.of(getGroups().get(2)));
        when(courseService.getCourseByName(lecture.getCourse().getName())).thenReturn(lecture.getCourse());
        when(lectureService.saveUpdateLecture(lecture)).thenReturn(List.of(lecture));
        when(lectureService.getLastLectureId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(lecture.getCourse()));
        when(lectureService.getAllLectures()).thenReturn(List.of(lecture));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/updating-lecture" +
                        "?lectureId=1&day=1&pair=1&course=TestCourse1&gIdOne=1&gIdTwo=2&gIdThree=3"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Lecture Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Lecture was updated " + lecture.getId() + "."))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newLectureId"))
                .andExpect(MockMvcResultMatchers.model().attribute("newLectureId", 2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listCourses"))
                .andExpect(MockMvcResultMatchers.model().attribute("listCourses", List.of(lecture.getCourse())))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listLectures"))
                .andExpect(MockMvcResultMatchers.model().attribute("listLectures", List.of(lecture)));
    }


    @Test
    @WithMockRolesStudentAndLecturer
    void testApplyLectureUpdateWithMockRolesStudentAndLecturer() throws Exception {
        Lecture lecture = getOneLecture();
        lecture.setGroups(getGroups());

        when(lectureService.getLecture(1)).thenReturn(List.of(lecture));
        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));
        when(groupService.getGroup(2)).thenReturn(List.of(getGroups().get(1)));
        when(groupService.getGroup(3)).thenReturn(List.of(getGroups().get(2)));
        when(courseService.getCourseByName(lecture.getCourse().getName())).thenReturn(lecture.getCourse());
        when(lectureService.saveUpdateLecture(lecture)).thenReturn(List.of(lecture));
        when(lectureService.getLastLectureId()).thenReturn(1);
        when(courseService.getAllCourses()).thenReturn(List.of(lecture.getCourse()));
        when(lectureService.getAllLectures()).thenReturn(List.of(lecture));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/staff/editing/lectures/updating-lecture" +
                        "?lectureId=1&day=1&pair=1&course=TestCourse1&gIdOne=1&gIdTwo=2&gIdThree=3"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    //    Data for tests
    private List<Student> getStudents() {
        List<Student> students = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            students.add(Student.builder().id((long) i).firstName("TestStudent" + i).lastName("TestStudent" + i).build());
        }
        return students;
    }

    private List<Lecturer> getLecturers() {
        List<Lecturer> lecturers = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            lecturers.add(Lecturer.builder().id(i).firstName("TestLecturer" + i).lastName("TestLecturer" + i).build());
        }
        return lecturers;
    }

    private List<Course> getCourses() {
        List<Course> courses = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            courses.add(Course.builder().id(i).name("TestCourse" + i).description("TestDescription" + i).build());
        }
        return courses;
    }

    private List<Group> getGroups() {
        List<Group> groups = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            groups.add(Group.builder().id(i).name("TT-" + i + i).build());
        }
        return groups;
    }

    private Lecture getOneLecture() {
        Lecture lecture = new Lecture();
        lecture.setId(1);
        lecture.setDayNumber(1);
        lecture.setPair(1);
        lecture.setCourse(getCourses().get(1));
        lecture.setGroups(getGroups());
        return lecture;
    }

    private List<Lecture> getLectures() {
        List<Lecture> lectures = new LinkedList<>();
        int pair = 1;
        for (int i = 0; i < 10; i++) {
            Lecture lecture = new Lecture();
            lecture.setId(i);
            lecture.setDayNumber(i / 2 + 1);
            pair = (pair == 4) ? 1 : pair;
            lecture.setPair(pair);
            lectures.add(lecture);
            pair++;
        }
        return lectures;
    }
}