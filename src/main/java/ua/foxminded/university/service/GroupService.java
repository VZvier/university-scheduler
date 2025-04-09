package ua.foxminded.university.service;

import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;

import java.util.List;

public interface GroupService {

	List<Group> getAllGroups();

	List<Group> getGroup(int groupId);

	List<Lecture> getGroupLectures(int groupId);

	List<Course> getGroupCourses(int groupId);

	void addNewGroups(List<Group> groups);

	void saveUpdateGroup(Group group);

	void removeGroup(Integer groupId);

	void removeGroupFromLecture(int groupId, int lectureId);

	void removeAllGroupCourses(int groupId);

	void removeGroupFromCourse(int groupId, int courseId);

	Integer getLastGroupId();

	boolean clearGroupsTable();
}
