package ua.foxminded.university.service;

import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Lecturer;

import java.util.List;

public interface LecturerService {

	List<Lecturer> getAllLecturers();

	List<Lecturer> getLecturer(int lecturerID);

	List<Course> getLecturerCourse(int lecturerID);

	void saveNewLecturers(List<Lecturer> lecturers);

	Lecturer saveUpdateLecturer(Lecturer lecturer);

	void removeLecturer(int lecturerID);

	void addLecturerToCourse(int lecturerID, int courseId);

	void removeLecturerFromCourse(int lecturerID);

	Integer getLastLecturerId();

	boolean clearLecturerTable();
}