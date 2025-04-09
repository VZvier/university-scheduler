package ua.foxminded.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "LECTURES")
public class Lecture implements Comparable<Lecture> {

	private static final int DURATION = 80;

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "Pair")
	private int pair;

	@ToString.Exclude
	@Column(name = "Day_Number")
	private int dayNumber;

	@Column(name = "Day")
	private DayOfWeek day;

	@Column(name = "Start")
	private String start;

	@Column(name = "End_Time")
	private String end;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@ToString.Exclude
	@JoinTable(name = "LECTUREGROUP", joinColumns = {@JoinColumn(name = "Lecture_ID")}, inverseJoinColumns = {
			@JoinColumn(name = "Group_ID")})
	private List<Group> groups;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "Course")
	@Nullable
	private Course course;


	public Lecture(Integer id, int dayNumber, int pair, Course course, List<Group> groups) {
		this.id = id;
		this.pair = pair;
		this.dayNumber = dayNumber;
		this.day = DayOfWeek.of(dayNumber);
		this.course = course;
		this.groups = groups;
		this.start = setStartTime();
		this.end = setEndTime();
	}

	private String setEndTime() {
		LocalTime endTime = LocalTime.parse(start).plusMinutes(DURATION);
		return endTime.getHour() + ":" + endTime.getMinute();
	}

	private String setStartTime() {
		String[] lectureBeginTime = {"09:00", "10:30", "12:00", "13:30"};
		String time;
		if (pair == 1) {
			time = lectureBeginTime[0];
		} else if (pair == 2) {
			time = lectureBeginTime[1];
		} else if (pair == 3) {
			time = lectureBeginTime[2];
		} else {
			time = lectureBeginTime[3];
		}
		return time;
	}

	public void setPair(int pair) {
		this.pair = pair;
		this.start = setStartTime();
		this.end = setEndTime();
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
		this.day = DayOfWeek.of(dayNumber);
	}

	private String getCourseName() {
		if (course != null) {
			return course.getName();
		} else {
			return "";
		}
	}

	private String getGroupNames() {
		StringBuilder sb = new StringBuilder();
		if (this.groups != null) {
			groups.forEach(g -> sb.append(" " + g.getId() + ": " + g.getName() + ", "));
		}
		return sb.toString();
	}

	public boolean isPresentAt(List<Lecture> lectures) {
		for (Lecture lecture : lectures) {
			boolean dayIsEquals = this.getDayNumber() == lecture.getDayNumber();
			boolean pairIsEquals = this.pair == lecture.getPair();
			boolean groupIsContains = isGroupsPresentInLecture(lecture.getGroups());
			if (dayIsEquals && pairIsEquals && groupIsContains) {
				return true;
			}
		}
		return false;
	}


	public boolean isGroupsPresentInLecture(List<Group> groups) {
		if (groups != null && !groups.isEmpty()) {
			for (Group checkingGroup : groups) {
				if (this.groups.contains(checkingGroup)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isGroupPresentInLecture(Group group) {
		if (group != null) {
			return this.groups.contains(group);
		}
		return false;
	}

	public boolean isAtSameTimeWithAny(List<Lecture> lectures) {
		for (Lecture lecture : lectures) {
			if ((this.dayNumber == lecture.getDayNumber())
					&& (this.pair == lecture.getPair())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "\nLecture [ID= " + id + ", pair=" + pair + ", day= " + day + ", time= " + start + "-" + end + ", groups="
				+ getGroupNames() + "course= " + getCourseName() + "]";
	}

	@Override
	public int compareTo(Lecture o) {
		return this.getId() - o.getId();
	}

	public static Comparator<Lecture> getDayComparator() {
		return new Comparator<Lecture>() {
			@Override
			public int compare(Lecture o1, Lecture o2) {
				return o1.getDayNumber() - o2.getDayNumber();
			}
		};
	}

	public static Comparator<Lecture> getPairComparator() {
		return new Comparator<Lecture>() {
			@Override
			public int compare(Lecture o1, Lecture o2) {
				return o1.getPair() - o2.getPair();
			}
		};
	}
}