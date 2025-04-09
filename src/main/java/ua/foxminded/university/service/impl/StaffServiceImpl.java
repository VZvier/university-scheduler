package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.*;
import ua.foxminded.university.repository.StaffRepository;
import ua.foxminded.university.service.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final LecturerService lecturerService;
    private final LectureService lectureService;
    private final CourseService courseService;
    private final GroupService groupService;

    StaffServiceImpl (StaffRepository repository,
                      LecturerService lecturerService,
                      LectureService lectureService,
                      CourseService courseService,
                      GroupService groupService) {
        this.staffRepository = repository;
        this.lecturerService = lecturerService;
        this.lectureService = lectureService;
        this.courseService = courseService;
        this.groupService = groupService;
    }

    @Override
    public List<Staff> getAllStaff() {
        log.info("Get all staff.");
        return staffRepository.findAll();
    }

    @Override
    public List<Staff> getStaffById(int id) {
        List<Staff> staff = new LinkedList<>();
        log.info("Get Staff by id.");
        Optional<Staff> staffOpt = staffRepository.findById(id);
        if (staffOpt.isPresent()){
            staff.add(staffOpt.get());
        }
        return staff;
    }

    @Override
    public boolean updateStaff(Staff staff) {
        log.info("Update staff №-{}, {} {}, position {}.", staff.getId(),
                staff.getFirstName(), staff.getLastName(), staff.getPosition());
        return staffRepository.update(staff.getId(), staff.getFirstName(), staff.getLastName(), staff.getPosition());

    }

    @Override
    public boolean saveNewStaff(List<Staff> staff) {
        log.info("Save staff list. {} entities.", staff.size());
        return staffRepository.saveAll(staff).size() != 0;
    }

    @Override
    public boolean removeStaff(int id) {
        log.info("Remove staff. {} entities.", id);
        staffRepository.deleteById(id);
        return true;
    }

    @Override
    public Integer getLastStaffId() {
        return staffRepository.getLastStaffId();
    }

    @Override
    public boolean clearStaffTable() {
        staffRepository.deleteAll();
        return true;
    }


    @Override
    public List<Course> assignLecturerToCourse(int lecturerId, int courseId) {
        lecturerService.addLecturerToCourse(lecturerId, courseId);
        return courseService.getAllCourses();
    }

    @Override
    public List<Course> assignLectureByIdToCourse(Integer lectureId, int courseId) {
            lectureService.addOrReplaceCourseToLecture(courseId, lectureId);
        return courseService.getAllCourses();
    }

    @Override
    public List<Course> assignGroupToCourse(int groupId, int courseId) {
        Group group = groupService.getGroup(groupId).get(0);
        Course course = courseService.getCourseById(courseId).get(0);

        List<Course> courses = group.getCourses();
        courses.add(course);
        group.setCourses(courses);

        groupService.saveUpdateGroup(group);
        return courseService.getAllCourses();
    }

    @Override
    public List<Course> unsignLecturerFromCourse(int lecturerId) {
        lecturerService.removeLecturerFromCourse(lecturerId);
        return courseService.getAllCourses();
    }

    @Override
    public List<Course> unsignGroupFromCourse(int groupId, int courseId) {
        Group group = groupService.getGroup(groupId).get(0);
        Course course = courseService.getCourseById(courseId).get(0);

        List<Course> courses = group.getCourses();
        courses.add(course);
        group.setCourses(courses);

        groupService.saveUpdateGroup(group);
        return courseService.getAllCourses();
    }

    @Override
    public List<Lecture> assignGroupToLecture(int groupId, int lectureId) {
        lectureService.addGroupToLecture(groupId, lectureId);
        return lectureService.getAllLectures();
    }

    @Override
    public List<Lecture> unAssignGroupFromLecture(int groupId, int lectureId) {
        lectureService.removeGroupFromLecture(groupId, lectureId);
        return lectureService.getAllLectures();
    }
}