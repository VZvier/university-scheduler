package ua.foxminded.university.service;

import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.entity.Lecturer;

import java.util.List;

public interface CourseService {

	List<Course> getAllCourses();

	List<Course> getCourseById(int courseId);

    Course getCourseByName(String name);

    List<Lecturer> getCourseLecturers(int courseId);

	List<Lecture> getCourseLectures(int courseId);

	List<Group> getGroupsOnCourse(int courseId);

	void addNewCourses(List<Course> courses);

	Course saveUpdateCourses(Course course);

	void removeCourse(int id);

	Integer getLastCoursesId();

	void addLecturesForCourse(int courseId, List<Lecture> lectures);

	boolean clearCoursesTable();

}
