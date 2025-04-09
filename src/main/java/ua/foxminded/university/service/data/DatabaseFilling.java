package ua.foxminded.university.service.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.foxminded.university.entity.*;
import ua.foxminded.university.service.*;

import java.util.List;

@Slf4j
@Component
public class DatabaseFilling {


    private final GroupService groupService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final StaffService staffService;
    private final LectureService lectureService;
    private final UserService userService;

    private final TestData data = new TestData();


    public DatabaseFilling(StudentService studentService,
                       GroupService groupService,
                       CourseService courseService,
                       LecturerService lecturerService,
                       LectureService lectureService,
                       UserService userService,
                       StaffService staffService
    ) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.courseService = courseService;
        this.lecturerService = lecturerService;
        this.lectureService = lectureService;
        this.userService = userService;
        this.staffService = staffService;
    }


    public void checkDBAndFillIfRequired() {
        if (isBaseFillRequired()) {
            clearDatabase();
            reFillDB();
        } else {
            log.info("Database is not empty! Continue program.");
        }
    }


    private boolean isBaseFillRequired() {
        log.info("Start check of DB is filled or not.");
        return isBaseClean();
    }

    private boolean isBaseClean() {
        return studentService.getAllStudents().isEmpty()
                || lecturerService.getAllLecturers().isEmpty()
                || courseService.getAllCourses().isEmpty()
                || groupService.getAllGroups().isEmpty()
                || staffService.getAllStaff().isEmpty()
                || userService.getAllUsers().isEmpty()
                || lectureService.getAllLectures().isEmpty();
    }

    private void clearDatabase() {
        studentService.clearStudentsTable();
        lecturerService.clearLecturerTable();
        courseService.clearCoursesTable();
        groupService.clearGroupsTable();
        lectureService.removeAllGroupsFromAllLecturesAndClearTable();
        lectureService.clearLectureTable();
        staffService.clearStaffTable();
        userService.clearUserTable();
        log.info("All DB tables are clear - {}!", isBaseClean());
    }

    private void  reFillDB() {
        List<String> fullNamesList = data.createFullNames();
        log.info("Start create Courses!");
        List<Course> courses = data.createCoursesFromFile();
        log.info("Start create Lecturers!");
        List<Lecturer> lecturers =
                data.separateLecturersToCourses(data.createLecturers(
                        data.selectRandomFullNames(fullNamesList, 100)), courses);

        log.info("Lecturers was created - {}! Like - {}", lecturers.size(), lecturers.get(99));
        log.info("Start create Staff!");
        List<Staff> staff = data.createStaff(data.selectRandomFullNames(fullNamesList, 50));
        staff.addAll(data.createAdmins(data.selectRandomFullNames(fullNamesList, 5)));
        log.info("Staff was created - {}! Like - {}", staff.size(), staff.get(55));

        log.info("Start create Students!");
        List<Student> students = data.createStudents(fullNamesList);
        log.info("Students were created - {}! Like - {}", students.size(), students.get(1500));


        log.info("Start separate students to groups.");
        List<Student> studentsInGroups = data.separateStudentsIntoGroups(students);
        log.info("Students were separated - {}!", students.size());
        log.info("Start fill Students table!");
        studentService.saveNewListOfStudents(studentsInGroups);
        log.info("Start fill Lecturers table!");
        lecturerService.saveNewLecturers(lecturers);

        log.info("Start fill Staff table!");
        staffService.saveNewStaff(staff);
        log.info("Staff table was filled!");
        fillGroupsCourseRelationsTable();
        fillLecturesTable();
        log.info("All Tables Was Filled successfully!!!");

    }

    private void fillGroupsCourseRelationsTable(){
        List<Course> courses = data.createCoursesFromFile();
        log.info("Start get groups from DB!");
        List<Group> groups = groupService.getAllGroups();
        log.info("Got groups from DB!\nStart separate Groups to Courses!");
        List<Group> groupsOnCourses = data.assignGroupsToCourses(groups, courses);
        log.info("Groups separated!");
        groupsOnCourses.forEach(System.out::println);
        groupService.addNewGroups(groupsOnCourses);
        log.info("Groups with courses saved!!!");
    }

    private void fillLecturesTable() {
        List<Group> groups = groupService.getAllGroups();
        log.info("Start create new lectures!");
        groups.forEach(group -> System.out.println(group.getName() + " {"
                + group.getCourses() + "}"));
        List<Lecture> lectures = data.createLecturesByGroups(groups);
        lectures.forEach(System.out::println);
        lectureService.addNewLectures(lectures);
        log.info("!! Time correction completed!!!");

    }
}
