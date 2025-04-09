package ua.foxminded.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "STUDENTS")
@ToString(includeFieldNames = false)
public class Student implements Comparable<Student> {

	@Id
	@Column(name = "ID")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Group_ID")
	@Nullable
	private Group group;

	@Column(name = "First_Name")
	private String firstName;

	@Column(name = "Last_Name")
	private String lastName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "User_ID")
	@Nullable
	private User user;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Student other = (Student) obj;
		return Objects.equals(id, other.id) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName);
	}

	@Override
	public int compareTo(Student o) {
		return (int) (this.getId() - o.getId());
	}
}