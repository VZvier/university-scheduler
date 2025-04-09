package ua.foxminded.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "GROUPS")
public class Group {

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "Name")
	private String name;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	@EqualsExclude
	@Nullable
	private List<Student> students = new LinkedList<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "GROUPS_COURSES_RELATIONS",
			joinColumns = {@JoinColumn(name = "Group_ID")},
			inverseJoinColumns = {@JoinColumn(name = "Course_ID")})
	@EqualsExclude
	@Nullable
	private List<Course> courses;


	@EqualsExclude
	@ToString.Exclude
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Nullable
	private List<Lecture> lectures;

	public Group(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public List<Group> getGroupsWithSameCourses(List<Group> groups){
		List<Group> result = new ArrayList<>();
		for (Group group: groups){
			if (group.getCourses() != null) {
				if (group.getCourses().containsAll(this.courses)) {
					result.add(group);
				}
			}
		}
		System.out.println("Group with same lectures " + result.size());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Group other = (Group) obj;
		return id == other.id && Objects.equals(name.strip(), other.name.strip());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "Group{" +
				"id=" + id +
				", name='" + name + '\'' +
				"; " + ((courses != null )?"Group courses=" + convCoursesToString(courses): "") +
				'}';
	}

	private String convCoursesToString(List<Course> list){
		StringBuilder sb  = new StringBuilder();
		for (Course c: list){
				 sb.append( c.getName().strip()).append(", ");
		}
		return sb.toString();
	}
}