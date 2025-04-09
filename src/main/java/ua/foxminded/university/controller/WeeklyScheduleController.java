package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.university.service.DaiLyScheduleService;

@Controller
public class WeeklyScheduleController {

    private static final String LECTURES = "listLectures";
    private static final String PAGE = "pagePurpose";
    private static final String PURPOSE = "COMMON TIMETABLE";
    private static final String MESSAGE = "jumbotronMessage";

    private final DaiLyScheduleService dSS;

    public WeeklyScheduleController(DaiLyScheduleService dSS){
        this.dSS = dSS;
    }

    @GetMapping("/common/students-weekly-timetable")
    public String studentSchedule(@RequestParam("studentId") int studentId, Model model){
        model.addAttribute(PAGE, "Student Weekly Timetable");
        model.addAttribute(MESSAGE, "Common schedule for period a week.");
        model.addAttribute(LECTURES, dSS.getLectures(dSS.getDailyScheduleForStudent(studentId)));
        return "pages/schedule";
    }

    @GetMapping("/lecturer/lecturers-weekly-timetable")
    public String lecturerSchedule(@RequestParam("lecturerId") int lecturerId, Model model){
        model.addAttribute(PAGE, "Lecturer Weekly Timetable");
        model.addAttribute(MESSAGE, "Common schedule for period a week.");
        model.addAttribute(LECTURES, dSS.getLectures(dSS.getDailyScheduleForLecturer(lecturerId)));
        return "pages/schedule";
    }

    @GetMapping("/lecturer/common-weekly-timetable")
    public String commonSchedule(Model model){
        model.addAttribute(PAGE, "COMMON TIMETABLE");
        model.addAttribute(MESSAGE, "Common schedule for period a week.");
        model.addAttribute(LECTURES, dSS.getLectures(dSS.getCommonDailySchedule()));
        return "pages/schedule";
    }
}
