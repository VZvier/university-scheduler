package ua.foxminded.university.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.service.impl.CourseServiceImpl;
import ua.foxminded.university.service.impl.LecturerServiceImpl;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {

	private static final String LIST_LECTURERS = "listLecturers";
	private static final String MESSAGE = "jumbotronMessage";
	private static final String PAGE = "pagePurpose";
	private static final String PURPOSE = "Lecturer Management";

	private final LecturerServiceImpl lecturerService;
	private final CourseServiceImpl courseService;

	public LecturerController (LecturerServiceImpl lecturerService, CourseServiceImpl courseService) {
		this.lecturerService = lecturerService;
		this.courseService = courseService;
	}

	@GetMapping("/all")
	public String getAllLecturers(Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "All lecturers available at database.");
		model.addAttribute("listCourses", courseService.getAllCourses());
		model.addAttribute(LIST_LECTURERS, lecturerService.getAllLecturers());
		return "pages/lecturers";
	}

	@GetMapping("/")
	public String getLecturerById(@RequestParam(name = "lecturerId") int lectureId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Lecturer with id = " + lectureId + ".");
		model.addAttribute("listCourses", courseService.getAllCourses());
		model.addAttribute(LIST_LECTURERS, lecturerService.getLecturer(lectureId));
		return "pages/lecturers";
	}

	@GetMapping("/course-lecturers")
	public String getCourseLecturer(@RequestParam(name = "courseId") int courseId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Lecturers who teach the course with id = " + courseId + ".");
		model.addAttribute("listCourses", courseService.getAllCourses());
		model.addAttribute(LIST_LECTURERS, courseService.getCourseLecturers(courseId));
		return "pages/lecturers";
	}

}