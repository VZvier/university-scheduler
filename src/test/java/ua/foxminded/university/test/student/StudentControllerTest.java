package ua.foxminded.university.test.student;

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
import ua.foxminded.university.controller.StudentController;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStudent;
import ua.foxminded.university.service.impl.StudentServiceImpl;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentServiceImpl studentService;


	@Test
	@WithMockRoleAdmin
	void testFindAllStudentsWithMockRoleAdmin() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getAllStudents()).thenReturn(students);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/lecturer/students/all"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"All students existing in database."))

				.andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
				.andExpect(MockMvcResultMatchers.model().attribute("listStudents",
						students));
	}

	@Test
	@WithMockRoleLecturer
	void testFindAllStudentsWithMockRoleLecturer() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getAllStudents()).thenReturn(students);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/lecturer/students/all"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"All students existing in database."))

				.andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
				.andExpect(MockMvcResultMatchers.model().attribute("listStudents",
						students));
	}

	@Test
	@WithMockRoleStudent
	void testFindAllStudentsWithMockRoleStudent() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getAllStudents()).thenReturn(students);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/lecturer/students/all"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void testFindAllStudentsWithAnonymous() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getAllStudents()).thenReturn(students);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/lecturer/students/all"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockRoleAdmin
	void testFindStudentByIdWithMockRoleAdmin() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getStudent(1)).thenReturn(List.of(students.get(0)));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/common/students/?studentId=1"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Student from database with Id= 1."))

				.andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
				.andExpect(MockMvcResultMatchers.model().attribute("listStudents",
						List.of(students.get(0))));
	}

	@Test
	@WithMockRoleLecturer
	void testFindStudentByIdWithMockRoleLecturer() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getStudent(1)).thenReturn(List.of(students.get(0)));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/common/students/?studentId=1"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Student from database with Id= 1."))

				.andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
				.andExpect(MockMvcResultMatchers.model().attribute("listStudents",
						List.of(students.get(0))));
	}

	@Test
	@WithMockRoleStudent
	void testFindStudentByIdWithMockRoleStudent() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getStudent(1)).thenReturn(List.of(students.get(0)));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/common/students/?studentId=1"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"Student from database with Id= 1."))

				.andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
				.andExpect(MockMvcResultMatchers.model().attribute("listStudents",
						List.of(students.get(0))));
	}

	@Test
	void testFindStudentByIdWithAnonymous() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getStudent(1)).thenReturn(List.of(students.get(0)));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/common/students/?studentId=1"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockRoleAdmin
	void testStudentsInGroupByGroupIdWithMockRoleAdmin() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getStudentsInGroup(1)).thenReturn(List.of(students.get(0)));

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/common/students/group-students?groupId=1"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"All students from database assigned to group with Id= 1."))

				.andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
				.andExpect(MockMvcResultMatchers.model().attribute("listStudents",
						List.of(students.get(0))));
	}

	@Test
	@WithMockRoleLecturer
	void testStudentsInGroupByGroupIdWithMockRoleLecturer() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getStudentsInGroup(1)).thenReturn(List.of(students.get(0)));

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/common/students/group-students?groupId=1"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"All students from database assigned to group with Id= 1."))

				.andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
				.andExpect(MockMvcResultMatchers.model().attribute("listStudents",
						List.of(students.get(0))));
	}

	@Test
	@WithMockRoleStudent
	void testStudentsInGroupByGroupIdWithMockRoleStudent() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getStudentsInGroup(1)).thenReturn(List.of(students.get(0)));

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/common/students/group-students?groupId=1"))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
				.andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Student Management"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
				.andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
						"All students from database assigned to group with Id= 1."))

				.andExpect(MockMvcResultMatchers.model().attributeExists("listStudents"))
				.andExpect(MockMvcResultMatchers.model().attribute("listStudents",
						List.of(students.get(0))));
	}

	@Test
	void testStudentsInGroupByGroupIdWithAnonymous() throws Exception {
		List<Student> students = getStudentsForTest();
		Mockito.when(studentService.getStudentsInGroup(1)).thenReturn(List.of(students.get(0)));

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/common/students/group-students?groupId=1"))

				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}


	//
	private List<Student> getStudentsForTest() {
		List<Student> students = new LinkedList<>();
		students.add(Student.builder().id((long) 1).group(new Group(1, "XX-11"))
				.firstName("TestN1").lastName("TestLN1")
				.build());
		students.add(Student.builder().id((long) 2).group(new Group(2, "XX-12"))
				.firstName("TestN2").lastName("TestLN2")
				.build());
		return students;
	}
}