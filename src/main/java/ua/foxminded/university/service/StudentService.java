package ua.foxminded.university.service;

import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Student;

import java.util.List;

public interface StudentService {

	List<Student> getAllStudents();

	List<Student> getStudent(int studentId);

	List<Student> getStudentsInGroup(int groupId);

	List<Group> getStudentGroup(int studentId);

	void addStudentToGroup(int studentId, int groupId);

	void removeStudentFromGroup(int studentId);

	void saveNewListOfStudents(List<Student> students);

	void removeStudent(int studentId);

	long getLastStudentId();

	boolean clearStudentsTable();

	Student saveUpdateStudent(Student student);
}
