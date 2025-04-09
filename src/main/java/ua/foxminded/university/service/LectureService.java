package ua.foxminded.university.service;

import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;

import java.util.List;

public interface LectureService {

	List<Lecture> getAllLectures();

	List<Lecture> getLecture(int lectureId);

	List<Course> getLectureCourse(int lectureId);

	List<Group> getLectureGroups(int lectureId);

	void addNewLectures(List<Lecture> lectures);

	List<Lecture> saveUpdateLecture(Lecture lecture);

	void removeLecture(int lectureId);

	public List<Lecture> addGroupToLecture(int groupId, int lectureId);

	public List<Lecture> removeGroupFromLecture(int groupId, int lectureId);

	public List<Lecture> addOrReplaceCourseToLecture(int courseId, int lectureId);

	boolean clearLectureTable();

	Integer getLastLectureId();

	boolean removeAllGroupsFromAllLecturesAndClearTable();
}
