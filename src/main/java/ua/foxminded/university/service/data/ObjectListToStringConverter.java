package ua.foxminded.university.service.data;

import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.entity.*;
import ua.foxminded.university.model.DailySchedule;

import java.util.Collections;
import java.util.List;

public class ObjectListToStringConverter {

	public String convertStudents(List<Student> students) {
		StringBuilder result = new StringBuilder();
		students.forEach(s -> result
				.append("\n" + s.getId() + " ")
				.append(s.getFirstName() + " ")
				.append(s.getLastName() + " ")
				.append(s.getGroup() + " "));
		return result.append("\n").toString();
	}

	public String convertLecturers(List<Lecturer> lecturers) {
		StringBuilder result = new StringBuilder();
		Collections.sort(lecturers);
		lecturers.forEach(lr -> result
				.append("\n")
				.append(lr.getId() + " ")
				.append(lr.getFirstName() + " ")
				.append(lr.getLastName() + " ")
				.append(lr.getCourse().getName()));
		return result.append("\n").toString();
	}

	public String convertGroups(List<Group> groups) {
		StringBuilder result = new StringBuilder();
		groups.forEach(g -> result
				.append("\n" + g.getId() + " ")
				.append(g.getName() + " "));
		return result.append("\n").toString();
	}

	public String convertCourses(List<Course> courses) {
		StringBuilder result = new StringBuilder();
		StringBuilder lecturers;
		for (Course c : courses) {
			lecturers = new StringBuilder();
			for (Lecturer l : c.getLecturers()) {
				lecturers.append(" |Lectors: " + ((c.getLecturers().size() > 1)
						? l.getId() + " " + l.getFirstName() + " " + l.getLastName() + ", "
						: l.getId() + " " + l.getFirstName() + " " + l.getLastName()));
			}
			result.append("\n" + c.getId() + " " + c.getName() + " " + c.getDescription() + lecturers.toString());
		}
		return result.append("\n").toString();
	}

	public String convertLectures(List<Lecture> lectures) {
		StringBuilder result = new StringBuilder();
		StringBuilder gr;
		for (Lecture l : lectures) {
			List<Group> groups = l.getGroups();
			gr = new StringBuilder();
			for (Group g : groups) {
				gr.append(g.getId() + ", ");
			}
			result.append("\n ID=" + l.getId() + ", Day - " + l.getDay() + ", Pair=" + l.getPair())
					.append(", Time: " + l.getStart() + "-" + l.getEnd() + ", Groups - " + gr.toString())
					.append("Course - " + l.getCourse().getName());
		}
		return result.append("\n").toString();
	}

	public String convertShedule(List<DailySchedule> schedule) {
		StringBuilder sb = new StringBuilder();
		for (DailySchedule s : schedule) {
			String converted = convertLectures(s.getLectures());
			sb.append(converted);
		}
		return sb.toString();
	}

	public String convertListStaffToSting(List<Staff> staffList){
		StringBuilder result = new StringBuilder();
		for (Staff staff : staffList){
			result.append("\nID=" + staff.getId())
					.append(", Full Name= " + staff.getFirstName())
					.append(" " + staff.getLastName())
					.append(", Position =" + staff.getPosition())
					.append(", User Login =" + staff.getUser().getLogin());
		}
		return result.toString();
	}

	public String convertListUsersToString(List<UserDto> users){
		StringBuilder result = new StringBuilder();
		for (UserDto user : users){
			result.append("\nID= " + user.getId())
					.append(", Login= " + user.getLogin())
					.append(", Password= " + user.getPassword())
					.append(", Role= " + user.getRole());
		}
		return result.toString();
	}


	public String convertListStringToString(List<String> list) {
		StringBuilder result = new StringBuilder();
		list.forEach(line -> result.append(line + "\n"));
		return result.toString();
	}
}