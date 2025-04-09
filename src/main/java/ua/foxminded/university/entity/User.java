package ua.foxminded.university.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import ua.foxminded.university.service.data.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User {
	
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "Login")
	private String login;

	@Transient
	private String password;

	@Column(name = "Password")
	private String encodedPassword;

	@Transient
	private String confirmPassword;
	
	@Column(name = "User_Role")
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@OneToOne(mappedBy = "user")
	@Nullable
	private Student student;
	
	@OneToOne(mappedBy = "user")
	@Nullable
	private Lecturer lecturer;

	@OneToOne(mappedBy = "user")
	@Nullable
	private Staff staff;

	public String getUsersName(){
		if (staff != null){
			return staff.getFirstName() + " " + staff.getLastName();
		}
		if (lecturer != null){
			return lecturer.getFirstName() + " " + lecturer.getLastName();
		}
		if (student != null){
			return student.getFirstName() + " " + student.getLastName();
		}
		return "";
	}

	public boolean isEmpty(){
		return (StringUtils.isBlank(this.login))
				|| ((StringUtils.isBlank(this.password)) && (StringUtils.isBlank(this.encodedPassword)))
				|| ((!StringUtils.isBlank(this.password)) && (StringUtils.isBlank(this.confirmPassword)));
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + encodedPassword + '\'' +
				", role=" + role +
				'}';
	}
}
