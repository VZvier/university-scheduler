package ua.foxminded.university.service;

import ua.foxminded.university.entity.*;

import java.util.List;

public interface StaffService {

    List<Staff> getAllStaff();

    List<Staff> getStaffById(int id);

    boolean updateStaff(Staff staff);

    boolean saveNewStaff(List<Staff> staff);

    boolean removeStaff(int id);

    Integer getLastStaffId();

    boolean clearStaffTable();

    List<Lecture> assignGroupToLecture(int groupId, int lectureId);

    List<Lecture> unAssignGroupFromLecture(int groupId, int lectureId);

    List<Course> assignLecturerToCourse(int lecturerId, int courseId);

    List<Course> unsignLecturerFromCourse(int lecturerId);

    List<Course> assignLectureByIdToCourse(Integer lectureId, int courseId);

    List<Course> assignGroupToCourse(int groupId, int courseId);

    List<Course> unsignGroupFromCourse(int groupId, int courseId);
}
