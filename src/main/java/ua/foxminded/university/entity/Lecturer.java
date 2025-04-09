package ua.foxminded.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "LECTURERS")
@ToString(includeFieldNames = false)
public class Lecturer implements Comparable<Lecturer> {

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "First_Name")
	private String firstName;

	@Column(name = "Last_Name")
	private String lastName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Course_ID")
	@Nullable
	private Course course;
	
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
		Lecturer other = (Lecturer) obj;
		return Objects.equals(firstName.strip(), other.firstName.strip()) && id == other.id
				&& Objects.equals(lastName.strip(), other.lastName.strip());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName.strip(), lastName.strip());
	}

	@Override
	public int compareTo(Lecturer o) {
		return this.getId() - o.getId();
	}
}