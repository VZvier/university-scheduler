package ua.foxminded.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.lang.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "COURSES")
@ToString(includeFieldNames = false)
public class Course implements Comparable<Course> {

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<Lecturer> lecturers;

	@ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsExclude
	@Nullable
	private List<Group> groups = new LinkedList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<Lecture> lectures;

	public Course(int id, String name, String descr) {
		this.id = id;
		this.name = name;
		this.description = descr;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Course other = (Course) obj;
		return Objects.equals(description.strip(), other.description.strip()) && id == other.id
				&& Objects.equals(name.strip(), other.name.strip());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description);
	}

	@Override
	public int compareTo(Course o) {
		return this.id - o.getId();
	}
}