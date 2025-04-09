package ua.foxminded.university.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.foxminded.university.service.impl.DailyScheduleServiceImpl;
import ua.foxminded.university.service.impl.PeriodScheduleServiceImpl;

@Controller
public class PeriodScheduleController {

	private static final String LIST_LECTURES = "listLectures";
	private static final String MESSAGE = "jumbotronMessage";
	private static final String PAGE = "pagePurpose";

	private final PeriodScheduleServiceImpl psSer;
	private final DailyScheduleServiceImpl dsSer;

	public PeriodScheduleController(PeriodScheduleServiceImpl psSer, DailyScheduleServiceImpl dsSer) {
		this.psSer = psSer;
		this.dsSer = dsSer;
	}

	@GetMapping("/common/students-period-timetable")
	public String getScheduleForStudent(@RequestParam("studentId") int studentId, @RequestParam("days") int days, Model model) {
		model.addAttribute(PAGE, "STUDENT'S TIMETABLE");
		model.addAttribute(MESSAGE, "Schedule for student with id = " + studentId + " for period = " + days + " days.");
		model.addAttribute(LIST_LECTURES, psSer.getLectures(psSer.getPeriodSchedule(days, dsSer.getDailyScheduleForStudent(studentId))));
		return "pages/schedule";
	}

	@GetMapping("/lecturer/lecturers-period-timetable")
	public String getScheduleForLecturer(@RequestParam("lecturerId") int lecturerId, @RequestParam("days") int days, Model model) {
		model.addAttribute(PAGE, "LECTURER'S TIMETABLE");
		model.addAttribute(MESSAGE, "Schedule for lecturer with id = " + lecturerId + " for period = " + days + " days.");
		model.addAttribute(LIST_LECTURES, psSer.getLectures(psSer.getPeriodSchedule(days, dsSer.getDailyScheduleForLecturer(lecturerId))));
		return "pages/schedule";
	}

	@GetMapping("/lecturer/common-period-timetable")
	public String getCommonSchedule(@RequestParam("days") int days, Model model) {
		model.addAttribute(PAGE, "COMMON TIMETABLE");
		model.addAttribute(MESSAGE, "Common schedule for period = " + days + " days.");
		model.addAttribute(LIST_LECTURES, psSer.getLectures((psSer.getPeriodSchedule(days, dsSer.getCommonDailySchedule()))));
		return "pages/schedule";
	}
}



