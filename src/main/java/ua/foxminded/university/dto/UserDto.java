package ua.foxminded.university.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class UserDto implements Comparable<UserDto>{

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "user_role")
    private String role;

    @Override
    public int compareTo(UserDto o) {
        return this.id - o.getId();
    }

    public static class UserRowMapper implements RowMapper<UserDto>{

        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDto user = new UserDto();
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("user_role"));
            return user;
        }
    }
}
