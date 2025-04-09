package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.university.service.impl.CourseServiceImpl;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.LectureServiceImpl;

@Controller
public class LectureController {

	private static final String LIST_LECTURE = "listLectures";
	private static final String PAGE = "pagePurpose";
	private static final String PURPOSE = "Lecture Management";
	private static final String MESSAGE = "jumbotronMessage";

	private final LectureServiceImpl lectureService;
	private final CourseServiceImpl courseService;
	private final GroupServiceImpl groupService;

	public LectureController (LectureServiceImpl lectureService,
							  CourseServiceImpl courseService,
							  GroupServiceImpl groupService) {
		this.lectureService = lectureService;
		this.courseService = courseService;
		this.groupService = groupService;
	}

	@GetMapping("/lecturer/lectures/all")
	public String getAllLectures(Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "All lectures available at database.");
		model.addAttribute("listCourses", courseService.getAllCourses());
		model.addAttribute("newLectureId", lectureService.getLastLectureId() + 1);
		model.addAttribute(LIST_LECTURE, lectureService.getAllLectures());
		return "/pages/lectures";
	}

	@GetMapping("/common/lectures/")
	public String getLectureById(@RequestParam(name = "lectureId") int lectureId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Lecture with id = " + lectureId + ".");
		model.addAttribute("listCourses", courseService.getAllCourses());
		model.addAttribute("newLectureId", lectureService.getLastLectureId() + 1);
		model.addAttribute(LIST_LECTURE, lectureService.getLecture(lectureId));
		return "/pages/lectures";
	}

	@GetMapping("/common/lectures/group-lectures")
	public String getLecturesForGroup(@RequestParam(name = "groupId") int groupId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "All lectures for group with id = " + groupId + ".");
		model.addAttribute("listCourses", courseService.getAllCourses());
		model.addAttribute("newLectureId", lectureService.getLastLectureId() + 1);
		model.addAttribute(LIST_LECTURE, groupService.getGroupLectures(groupId));
		return "/pages/lectures";
	}

	@GetMapping("/lecturer/lectures/course-lectures")
	public String getLecturesForCourse(@RequestParam(name = "courseId") int courseId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "All lectures for course with id = " + courseId + ".");
		model.addAttribute("listCourses", courseService.getAllCourses());
		model.addAttribute("newLectureId", lectureService.getLastLectureId() + 1);
		model.addAttribute(LIST_LECTURE, courseService.getCourseLectures(courseId));
		return "/pages/lectures";
	}
}
