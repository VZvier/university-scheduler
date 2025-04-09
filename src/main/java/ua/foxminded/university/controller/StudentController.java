package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.university.service.impl.StudentServiceImpl;

@Controller
public class StudentController {

	private static final String LIST_STUDENTS = "listStudents";

	private final StudentServiceImpl studentService;

	public StudentController(StudentServiceImpl studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/lecturer/students/all")
	public String getStudents(Model model) {
		model.addAttribute("pagePurpose", "Student Management");
		model.addAttribute("jumbotronMessage", "All students existing in database.");
		model.addAttribute("idForNewStudent", studentService.getLastStudentId() +1);
		model.addAttribute(LIST_STUDENTS, studentService.getAllStudents());
		return "pages/students";
	}

	@GetMapping("/common/students/")
	public String getStudentById(@RequestParam("studentId")int studentId, Model model) {
		model.addAttribute("pagePurpose", "Student Management");
		model.addAttribute("jumbotronMessage", "Student from database with Id= "+ studentId + ".");
		model.addAttribute("idForNewStudent", studentService.getLastStudentId() +1);
		model.addAttribute(LIST_STUDENTS, studentService.getStudent(studentId));
		return "pages/students";
	}

	@GetMapping("/common/students/group-students")
	public String getStudentsInGroup(@RequestParam(name = "groupId") int groupId, Model model) {
		model.addAttribute("pagePurpose", "Student Management");
		model.addAttribute("jumbotronMessage",
				"All students from database assigned to group with Id= "+ groupId + ".");
		model.addAttribute("idForNewStudent", studentService.getLastStudentId() +1);
		model.addAttribute(LIST_STUDENTS, studentService.getStudentsInGroup(groupId));
		return "pages/students";
	}
}
