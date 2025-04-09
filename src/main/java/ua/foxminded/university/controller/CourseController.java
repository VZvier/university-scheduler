package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.university.service.impl.CourseServiceImpl;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.LectureServiceImpl;
import ua.foxminded.university.service.impl.LecturerServiceImpl;

@Controller
@RequestMapping("/common/courses")
public class CourseController {

	private static final String LIST_COURSES = "listCourses";
	private static final String PAGE = "pagePurpose";
	private static final String PURPOSE = "Course Management";
	private static final String MESSAGE = "jumbotronMessage";


	private final CourseServiceImpl courseService;
	private final LecturerServiceImpl lecturerService;
	private final LectureServiceImpl lectureService;
	private final GroupServiceImpl groupService;

	public CourseController(CourseServiceImpl courseService, LecturerServiceImpl lecturerService,
							LectureServiceImpl lectureService, GroupServiceImpl groupService) {

		this.courseService = courseService;
		this.lecturerService = lecturerService;
		this.lectureService = lectureService;
		this.groupService = groupService;
	}

	@GetMapping("/all")
	public String findAllCourses(Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "All courses present in the database.");
		model.addAttribute("idForNewCourse", courseService.getLastCoursesId() + 1);
		model.addAttribute(LIST_COURSES, courseService.getAllCourses());
		return "pages/courses";
	}

	@GetMapping("/")
	public String findCourseById(@RequestParam(name = "courseId") int courseId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Course with id = " + courseId + ".");
		model.addAttribute("idForNewCourse", courseService.getLastCoursesId() + 1);
		model.addAttribute(LIST_COURSES, courseService.getCourseById(courseId));
		return "pages/courses";
	}

	@GetMapping("/lecturer-course")
	public String findCourseOfLecturer(@RequestParam(name = "lecturerId") int lecturerId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Course taught by the Lecturer with id = " + lecturerId + ".");
		model.addAttribute("idForNewCourse", courseService.getLastCoursesId() + 1);
		model.addAttribute(LIST_COURSES, lecturerService.getLecturerCourse(lecturerId));
		return "pages/courses";
	}

	@GetMapping("/lecture-course")
	public String findCourseOfLecture(@RequestParam(name = "lectureId") int lectureId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Course taught in lecture with id = " + lectureId + ".");
		model.addAttribute("idForNewCourse", courseService.getLastCoursesId() + 1);
		model.addAttribute(LIST_COURSES, lectureService.getLectureCourse(lectureId));
		return "pages/courses";
	}

	@GetMapping("/group-courses")
	public String findGroupCourses(@RequestParam(name = "groupId") int groupId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Courses for group №" + groupId + ".");
		model.addAttribute("idForNewCourse", courseService.getLastCoursesId() + 1);
		model.addAttribute(LIST_COURSES, groupService.getGroupCourses(groupId));
		return "pages/courses";
	}
}
