package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.university.service.impl.CourseServiceImpl;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.LectureServiceImpl;
import ua.foxminded.university.service.impl.StudentServiceImpl;

@Controller
@RequestMapping("/common/groups")
public class GroupController {

	private static final String LIST_GROUPS = "listGroups";
	private static final String PAGE = "pagePurpose";
	private static final String  PURPOSE = "Group Management";
	private static final String MESSAGE = "jumbotronMessage";

	private final StudentServiceImpl studentService;
	private final GroupServiceImpl groupService;
	private final LectureServiceImpl lectureService;
	private final CourseServiceImpl courseService;

	public GroupController(StudentServiceImpl studentService, GroupServiceImpl groupService,
						   LectureServiceImpl lectureService, CourseServiceImpl courseService) {

		this.studentService = studentService;
		this.groupService = groupService;
		this.lectureService = lectureService;
		this.courseService = courseService;
	}

	@GetMapping("/all")
	public String getAllGroups(Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "All available groups from database.");
		model.addAttribute("newGroupId", groupService.getLastGroupId() + 1);
		model.addAttribute(LIST_GROUPS, groupService.getAllGroups());
		return "pages/groups";
	}

	@GetMapping("/")
	public String getGroupById(@RequestParam(name = "groupId") int groupId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Group by id = " + groupId + ".");
		model.addAttribute("newGroupId", groupService.getLastGroupId() + 1);
		model.addAttribute(LIST_GROUPS, groupService.getGroup(groupId));
		return "pages/groups";
	}

	@GetMapping("/student-group")
	public String getStudentsGroup(@RequestParam(name = "studentId") int studentId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Group of student with id = " + studentId + ".");
		model.addAttribute("newGroupId", groupService.getLastGroupId() + 1);
		model.addAttribute(LIST_GROUPS, studentService.getStudentGroup(studentId));
		return "pages/groups";
	}

	@GetMapping("/lecture-groups")
	public String getGroupsOnLecture(@RequestParam(name = "lectureId") int lectureId, Model model) {
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, "Groups of lecture with id = " + lectureId + ".");
		model.addAttribute("newGroupId", groupService.getLastGroupId() + 1);
		model.addAttribute(LIST_GROUPS, lectureService.getLectureGroups(lectureId));
		return "pages/groups";
	}

	@GetMapping("/course-groups")
	public String getGroupsOnCourse(@RequestParam(name = "courseId") int courseId, Model model){
		model.addAttribute(PAGE, PURPOSE);
		model.addAttribute(MESSAGE, String.format("Groups on course №%s.", courseId));
		model.addAttribute("newGroupId", groupService.getLastGroupId() + 1);
		model.addAttribute(LIST_GROUPS, courseService.getGroupsOnCourse(courseId));
	return "pages/groups";
	}
}
