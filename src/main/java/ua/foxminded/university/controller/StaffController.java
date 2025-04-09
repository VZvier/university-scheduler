package ua.foxminded.university.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.entity.*;
import ua.foxminded.university.service.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/staff/editing")
public class StaffController {

    private static final String PAGE = "pagePurpose";
    private static final String JUMBOTRON = "jumbotronMessage";

    private final CourseService courseService;
    private final GroupService groupService;
    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final LectureService lectureService;

    private final StaffService staffService;

    StaffController(CourseService courseService,
                    GroupService groupService,
                    StudentService studentService,
                    LecturerService lecturerService,
                    LectureService lectureService,
                    StaffService staffService) {
        this.courseService = courseService;
        this.groupService = groupService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.lectureService = lectureService;
        this.staffService = staffService;
    }

    @PostMapping("/students/new-student")
    public String addNewStudent(
            @RequestParam(name = "studentId") Integer studentId,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "groupId") Integer groupId,
            Model model) {

        Student student = studentService.saveUpdateStudent(Student.builder()
                .id((studentId != null) ? (long) studentId : studentService.getLastStudentId() + 1)
                .firstName(firstName).lastName(lastName)
                .group(((groupId != null) ? groupService.getGroup(groupId).get(0) : null)).build());
        model.addAttribute(PAGE, "Student Management");
        model.addAttribute(JUMBOTRON, String.format("Student saved %s, %s, %s, %s!", student.getId(),
                firstName, lastName, (student.getGroup() != null) ? student.getGroup() : null));
        model.addAttribute("listStudents",
                (groupId != null) ? studentService.getStudentsInGroup(groupId) :
                        studentService.getStudent(Math.toIntExact(student.getId())));

        return "pages/students";
    }

    @PostMapping("/lecturers/new-lecturer")
    public String addNewLecturer(
            @RequestParam(name = "fName") String firstName,
            @RequestParam(name = "lName") String lastName,
            @RequestParam(name = "course", required = false) String course, Model model) {

        lecturerService.saveUpdateLecturer(Lecturer.builder().id(lecturerService.getLastLecturerId() + 1)
                .firstName(firstName).lastName(lastName)
                .course(courseService.getCourseByName(course)).build());
        model.addAttribute(PAGE, "Lecturer Management");
        model.addAttribute(JUMBOTRON, String.format("Lecturer saved %s %s!", firstName, lastName));
        model.addAttribute("listCourses", courseService.getAllCourses());
        model.addAttribute("listLecturers", lecturerService.getAllLecturers());
        return "pages/lecturers";
    }

    @PostMapping("/groups/new-group")
    public String addNewGroup(
            @RequestParam(name = "groupId") Integer groupId,
            @RequestParam(name = "name") String name, Model model) {

        if (groupId != null && !StringUtils.isBlank(name)) {
            groupService.saveUpdateGroup(Group.builder().id(groupId).name(name).courses(new LinkedList<>()).build());
        }
        model.addAttribute(PAGE, "Group Management");
        model.addAttribute(JUMBOTRON, String.format("Group saved %s!", name));
        model.addAttribute("newGroupId", groupService.getLastGroupId() + 1);
        model.addAttribute("listGroups", groupService.getAllGroups());
        return "pages/groups";
    }

    @PostMapping("/courses/new-course")
    public String addNewCourse(
            @RequestParam(name = "courseId") Integer courseId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description", required = false) String description,
            Model model) {

        String msg = "";
        if (courseId != null && !StringUtils.isBlank(name)) {
            courseService.saveUpdateCourses(Course.builder().id(courseId).name(name).description(description).build());
            msg = "saved" + name;
        } else {
            msg = "not saved";
        }
        model.addAttribute(PAGE, "Course Management");
        model.addAttribute(JUMBOTRON, String.format("Course %s!", "saved" + name));
        model.addAttribute("idForNewCourse", courseService.getLastCoursesId() + 1);
        model.addAttribute("listCourses", courseService.getAllCourses());
        return "pages/courses";
    }


    @PostMapping("/lectures/new-lecture")
    public String addNewLecture(@RequestParam(name = "lectureId") Integer lectureId,
                                @RequestParam(name = "day") Integer day,
                                @RequestParam(name = "pair") Integer pair,
                                @RequestParam(name = "course") String course, Model model,
                                @RequestParam(name = "gIdOne", required = false) Integer gIdOne,
                                @RequestParam(name = "gIdTwo", required = false) Integer gIdTwo,
                                @RequestParam(name = "gIdThree", required = false) Integer gIdThree) {

        List<Lecture> lectureAsList = new LinkedList<>();
        List<Group> groups = new LinkedList<>();
        if (gIdOne != null){
            groups.add(groupService.getGroup(gIdOne).get(0));
        }
        if (gIdTwo != null){
            groups.add(groupService.getGroup(gIdTwo).get(0));
        }
        if (gIdThree != null){
            groups.add(groupService.getGroup(gIdThree).get(0));
        }
        if ((day != null) && (pair != null) && (!StringUtils.isBlank(course))) {
            lectureAsList = lectureService.saveUpdateLecture(new Lecture(lectureId, day, pair,
                    courseService.getCourseByName(course), groups));
        }
        model.addAttribute(PAGE, "Lecture Management");
        model.addAttribute(JUMBOTRON, "Saved new Lecture with id= " + lectureAsList.get(0).getId() + "!");
        model.addAttribute("listCourses", courseService.getAllCourses());
        model.addAttribute("newLectureId", lectureService.getLastLectureId() + 1);
        model.addAttribute("listLectures", lectureAsList);

        return "pages/lectures";
    }

    @PostMapping("/students/removing-student")
    public String removeStudent(@RequestParam(name = "studentId") int studentId, Model model) {
        log.info("Remove student with id = {}", studentId);
        studentService.removeStudent(studentId);
        model.addAttribute(PAGE, "Student Management");
        model.addAttribute(JUMBOTRON, "Student with id=" + studentId + " was deleted!");
        model.addAttribute("listStudents", studentService.getAllStudents());
        return "pages/students";
    }

    @PostMapping("/lecturers/removing-lecturer")
    public String removeLecturer(@RequestParam(name = "lecturerId") int lecturerId, Model model) {
        log.info("Remove lecturer with id = {}", lecturerId);
        lecturerService.removeLecturer(lecturerId);
        model.addAttribute(PAGE, "Lecturer Management");
        model.addAttribute(JUMBOTRON, "Lecturer with id=" + lecturerId + " was deleted!");
        model.addAttribute("listCourses", courseService.getAllCourses());
        model.addAttribute("listLecturers", lecturerService.getAllLecturers());
        return "pages/lecturers";
    }

    @PostMapping("/courses/removing-course")
    public String removeCourse(@RequestParam(name = "courseId") int courseId, Model model) {
        log.info("Remove course with id = {}", courseId);
        Course course = courseService.getCourseById(courseId).get(0);
        for (Lecture lecture : course.getLectures()){
            lectureService.removeLecture(lecture.getId());
        }
        for (Lecturer lecturer : course.getLecturers()){
            lecturerService.removeLecturerFromCourse(lecturer.getId());
        }
        if (course.getGroups() != null && !course.getGroups().isEmpty()) {
            for (Group group : course.getGroups()) {
                groupService.removeGroupFromCourse(Math.toIntExact(group.getId()), courseId);
            }
        }
        courseService.removeCourse(courseId);
        model.addAttribute(PAGE, "Courses Management");
        model.addAttribute(JUMBOTRON, "Course with id=" + courseId + " was deleted!");
        model.addAttribute("listCourses", courseService.getAllCourses());
        return "pages/courses";
    }

    @PostMapping("/groups/removing-group")
    public String removeGroup(@RequestParam(name = "groupId") int groupId, Model model) {
        log.info("Remove group with id = {}", groupId);
        List<Lecture> groupLectures = groupService.getGroupLectures(groupId);
        List<Course> groupCourses = groupService.getGroupCourses(groupId);
        if (!groupLectures.isEmpty()) {
            for (Lecture lecture : groupLectures) {
                lectureService.removeGroupFromLecture(groupId, lecture.getId());
            }
        }
        if ((groupCourses != null) && (!groupCourses.isEmpty())){
            groupService.removeAllGroupCourses(groupId);
            studentService.getStudentsInGroup(groupId);
            for (Student student: studentService.getStudentsInGroup(groupId)) {
                studentService.removeStudentFromGroup(Math.toIntExact(student.getId()));
            }
            groupService.removeGroup(groupId);
        }
        model.addAttribute(PAGE, "Group Management");
        model.addAttribute(JUMBOTRON, "Group with id=" + groupId + " was deleted!");
        model.addAttribute("newGroupId", groupService.getLastGroupId() + 1);
        model.addAttribute("listGroups", groupService.getAllGroups());
        return "pages/groups";
    }

    @PostMapping("/lectures/removing-lecture")
    public String removeLecture(@RequestParam(name = "lectureId") int lectureId, Model model) {
        log.info("Remove lecture with id = {}", lectureId);
        lectureService.removeLecture(lectureId);
        model.addAttribute(PAGE, "Lecture Management");
        model.addAttribute(JUMBOTRON, "Lecture with id=" + lectureId + " was deleted!");
        model.addAttribute("newLectureId", lectureService.getLastLectureId() + 1);
        model.addAttribute("listCourses", courseService.getAllCourses());
        model.addAttribute("listLectures", lectureService.getAllLectures());
        return "pages/lectures";
    }

    @GetMapping("/courses/assigning/")
    public String assignGroupOrLecturerOrLectureToCourse(
            @RequestParam(name = "groupId", required = false) Integer groupId,
            @RequestParam(name = "lecturerId", required = false) Integer lecturerId,
            @RequestParam(name = "lectureId", required = false) Integer lectureId,
            @RequestParam(name = "courseId") Integer courseId, Model model) {

        List<Course> courses = new LinkedList<>();
        String message = "";
        if (groupId != null && courseId != null) {
            courses = staffService.assignGroupToCourse(groupId, courseId);
            message = "Group №" + groupId + " assigned to Course";
        } else if (lecturerId != null && courseId != null) {
            courses = staffService.assignLecturerToCourse(lecturerId, courseId);
            message = "Lecturer №" + lecturerId + " assigned to Course";
        } else if (lectureId != null && courseId != null) {
            courses = staffService.assignLectureByIdToCourse(lectureId, courseId);
            message = "Lecture №" + lectureId + " assigned to Course";
        } else {
            log.info("No any parameter for assigning!");
        }
        model.addAttribute(PAGE, "Courses Management");
        model.addAttribute(JUMBOTRON, message);
        model.addAttribute("listCourses", courses);
        return "pages/courses";
    }

    @GetMapping("/courses/unsigning/")
    public String unsignGroupOrLectureOrLecturerFromCourse(
            @RequestParam(name = "lecturerId", required = false) Integer lecturerId,
            @RequestParam(name = "groupId", required = false) Integer groupId,
            @RequestParam(name = "courseId", required = false) Integer courseId, Model model) {

        List<Course> courses = new LinkedList<>();
        String message = "";
        if (groupId != null && courseId != null) {
            courses = staffService.unsignGroupFromCourse(groupId, courseId);
            message = "Group № " + groupId + " unassigned from Course";
        } else if (lecturerId != null) {
            courses = staffService.unsignLecturerFromCourse(lecturerId);
            message = "Lecturer № " + lecturerId + " unassigned from Course";
        } else {
            log.error("No any parameter for un assigning!");
        }
        model.addAttribute(PAGE, "Courses Management");
        model.addAttribute(JUMBOTRON, message);
        model.addAttribute("listCourses", courses);
        return "pages/courses";
    }

    @GetMapping("/groups/adding-lecture")
    public String assignGroupToLecture(
            @RequestParam(name = "groupId") int groupId,
            @RequestParam(name = "lectureId") int lectureId,
            Model model) {

        model.addAttribute(PAGE, "Lecture Management");
        model.addAttribute(JUMBOTRON,
                "Group " + groupId + " was added to lecture " + lectureId + "!");
        model.addAttribute("listLectures", lectureService.addGroupToLecture(groupId, lectureId));
        return "pages/lectures";
    }

    @GetMapping("/students/adding-group")
    public String assignStudentToGroup(
            @RequestParam(name = "groupId") int groupId,
            @RequestParam(name = "studentId") int studentId,
            Model model) {

        studentService.addStudentToGroup(studentId, groupId);
        model.addAttribute(PAGE, "Student Management");
        model.addAttribute(JUMBOTRON,
                "Student " + studentId + " was added to group " + groupId + "!");
        model.addAttribute("listStudents", studentService.getStudent(studentId));
        return "pages/students";
    }

    @GetMapping("/groups/removing-lecture")
    public String unsignGroupFromLecture(
            @RequestParam(name = "groupId") int groupId,
            @RequestParam(name = "lectureId") int lectureId,
            Model model) {

        lectureService.removeGroupFromLecture(groupId, lectureId);
        model.addAttribute(PAGE, "Lecture Management");
        model.addAttribute(JUMBOTRON, "Group " + groupId + " was deleted from lecture " + lectureId + "!");
        model.addAttribute("listLectures", lectureService.getLecture(lectureId));
        return "pages/lectures";
    }

    @GetMapping("/students/removing-group")
    public String unsignStudentFromGroup(@RequestParam(name = "studentId") int studentId, Model model) {

        studentService.removeStudentFromGroup(studentId);
        model.addAttribute(PAGE, "Student Management");
        model.addAttribute(JUMBOTRON,
                "Student " + studentId + " was removed from his group!");
        model.addAttribute("listStudents", studentService.getStudent(studentId));
        return "pages/students";
    }

    @GetMapping("/students/updating-student")
    public String getStudentUpdatePage(@RequestParam(name = "studentId") Integer studentId, Model model) {
        model.addAttribute(PAGE, "Update Student Page");
        model.addAttribute("action", "update");
        int lastGroupNumber = groupService.getLastGroupId();
        model.addAttribute("jumbotronMessage",
                "You can update first name, last name, or change group by id till " + lastGroupNumber + "!" +
                        "\nFor remove group set groupId = 0");
        model.addAttribute("student", studentService.getStudent(studentId).get(0));

        return "pages/students";
    }

    @PostMapping("/students/updating-student")
    public String applyStudentUpdate(
            @RequestParam(name = "studentId") Integer studentId,
            @RequestParam(name = "firstName", required = false) String fName,
            @RequestParam(name = "lastName", required = false) String lName,
            @RequestParam(name = "groupId", required = false) Integer groupId,
            Model model) {

        Student student = studentService.getStudent(studentId).get(0);
        if (!StringUtils.isBlank(fName)) {
            student.setFirstName(fName);
        }
        if (!StringUtils.isBlank(lName)) {
            student.setLastName(lName);
        }
        if (groupId != null) {
            student.setGroup(groupService.getGroup(groupId).get(0));
        }
        studentService.saveUpdateStudent(student);
        model.addAttribute(PAGE, "Student Management");
        model.addAttribute("jumbotronMessage", "Student was updated " + studentId + ".");
        model.addAttribute("listStudents", studentService.getStudent(studentId));
        return "pages/students";
    }

    @GetMapping("/lecturers/updating-lecturer")
    public String getLecturerUpdatePage(@RequestParam(name = "lecturerId") int lecturerId, Model model) {
        model.addAttribute(PAGE, "Update Lecturer Page");
        model.addAttribute("action", "update");
        model.addAttribute("jumbotronMessage", "You can update first name, last name, or change subject!");
        model.addAttribute("listCourses", courseService.getAllCourses());
        model.addAttribute("lecturer", lecturerService.getLecturer(lecturerId).get(0));
        return "pages/lecturers";
    }

    @PostMapping("/lecturers/updating-lecturer")
    public String updateLecturer(
            @RequestParam(name = "lecturerId") Integer lecturerId,
            @RequestParam(name = "firstName", required = false) String fName,
            @RequestParam(name = "lastName", required = false) String lName,
            @RequestParam(name = "course", required = false) String course,
            Model model) {

        Lecturer lecturer = lecturerService.getLecturer(lecturerId).get(0);
        if (!StringUtils.isBlank(fName)) {
            lecturer.setFirstName(fName);
        }
        if (!StringUtils.isBlank(lName)) {
            lecturer.setLastName(lName);
        }
        if (!StringUtils.isBlank(course)) {
            lecturer.setCourse(courseService.getCourseByName(course));
        }
        lecturerService.saveUpdateLecturer(lecturer);
        model.addAttribute(PAGE, "Lecturer Management");
        model.addAttribute("jumbotronMessage", "Lecturer was updated " + lecturer + ".");
        model.addAttribute("listCourses", courseService.getAllCourses());
        model.addAttribute("listLecturers", lecturerService.getAllLecturers());
        return "pages/lecturers";
    }

    @GetMapping("/courses/updating-course")
    public String getCourseUpdatePage(@RequestParam(name = "courseId") int courseId, Model model) {

        model.addAttribute(PAGE, "Update Course Page");
        model.addAttribute("action", "update");
        model.addAttribute("jumbotronMessage", "You can update course name or description!");
        model.addAttribute("course", courseService.getCourseById(courseId).get(0));
        return "pages/courses";
    }

    @PostMapping("/courses/updating-course")
    public String applyCourseUpdate(
            @RequestParam(name = "courseId") Integer courseId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description, Model model) {

        Course course = courseService.getCourseById(courseId).get(0);
        if (!StringUtils.isBlank(name)) {
            course.setName(name);
        }
        if (!StringUtils.isBlank(description)) {
            course.setDescription(description);
        }
        courseService.saveUpdateCourses(course);
        model.addAttribute(PAGE, "Course Management");
        model.addAttribute("jumbotronMessage", "Course was updated " + course + ".");
        model.addAttribute("idForNewCourse", courseService.getLastCoursesId() + 1);
        model.addAttribute("listCourses", courseService.getAllCourses());
        return "pages/courses";
    }

    @GetMapping("/groups/updating-group")
    public String getGroupUpdatePage(@RequestParam(name = "groupId") int groupId, Model model) {
        model.addAttribute(PAGE, "Update Group Page");
        model.addAttribute("action", "update");
        model.addAttribute("jumbotronMessage", "You can update group name!");
        model.addAttribute("group", groupService.getGroup(groupId).get(0));
        return "pages/groups";
    }

    @PostMapping("/group/updating-group")
    public String applyGroupUpdate(
            @RequestParam(name = "groupId") Integer groupId,
            @RequestParam(name = "name") String name,
            Model model) {

        Group group = groupService.getGroup(groupId).get(0);
        if (!StringUtils.isBlank(name)) {
            group.setName(name);
        }
        groupService.saveUpdateGroup(group);
        model.addAttribute(PAGE, "Groups Management");
        model.addAttribute("jumbotronMessage", "Group was updated " + group + ".");
        model.addAttribute("newGroupId", groupService.getLastGroupId() + 1);
        model.addAttribute("listGroups", groupService.getAllGroups());
        return "pages/groups";
    }

    @GetMapping("/lectures/updating-lecture")
    public String getLectureUpdatePage(@RequestParam(name = "lectureId") int lectureId, Model model) {
        model.addAttribute(PAGE, "Update Lecture Page");
        model.addAttribute("action", "update");
        model.addAttribute("jumbotronMessage",
                "You can update lecture №" + lectureId + " by changing day, pair, course or adding group!");

        model.addAttribute("listCourses", courseService.getAllCourses());
        model.addAttribute("lecture", lectureService.getLecture(lectureId).get(0));
        return "pages/lectures";
    }

    @PostMapping("/lectures/updating-lecture")
    public String applyLectureUpdate(
            @RequestParam(name = "lectureId") Integer lectureId,
            @RequestParam(name = "day", required = false) Integer day,
            @RequestParam(name = "pair", required = false) Integer pair,
            @RequestParam(name = "course", required = false) String course,
            @RequestParam(name = "gIdOne", required = false) Integer gIdOne,
            @RequestParam(name = "gIdTwo", required = false) Integer gIdTwo,
            @RequestParam(name = "gIdThree", required = false) Integer gIdThree,
            Model model) {

        List<Integer> ints = Arrays
                .asList((gIdOne != null?gIdOne:0), (gIdTwo != null? gIdTwo:0), (gIdThree != null?gIdThree:0));

        Lecture lecture = lectureService.getLecture(lectureId).get(0);
        List<Group> groups = remakeListGroups(ints);

        if ((day != null) && (lecture.getDayNumber() != day)){
            lecture.setDayNumber(day);
        }
        if  ((pair != null) && (lecture.getPair() != pair)){
            lecture.setPair(pair);
        }
        if  ((course != null) && (!lecture.getCourse().getName().equals(course))){
            lecture.setCourse(courseService.getCourseByName(course));
        }
        lecture.setGroups(groups);
        lectureService.saveUpdateLecture(lecture);

        model.addAttribute(PAGE, "Lecture Management");
        model.addAttribute("jumbotronMessage", "Lecture was updated " + lectureId + ".");
        model.addAttribute("newLectureId", lectureService.getLastLectureId() + 1);
        model.addAttribute("listCourses", courseService.getAllCourses());
        model.addAttribute("listLectures", lectureService.getAllLectures());
        return "pages/lectures";
    }

    private List<Group> remakeListGroups(List<Integer> gIds){
        List<Group> groups = new LinkedList<>();
        for (Integer i: gIds) {
            groups.add(groupService.getGroup(i).get(0));
        }
        return groups;
    }
}