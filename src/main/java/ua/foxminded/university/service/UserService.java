package ua.foxminded.university.service;

import org.springframework.security.core.userdetails.UserDetails;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.service.data.UserRole;

import java.util.List;

public interface UserService {

	List<UserDetails> convertUserDtoToUserDetails(List<UserDto> users);

	UserDetails convertUserDtoToUserDetails(UserDto user);

	List<UserDto> getAllUsers();

	boolean saveUser(User user);
	
	boolean removeUser(Long userId);

    List<UserDto> getUserByRole(UserRole role);

    UserDto getUserById(Integer userId);
	
	Integer saveUserList(List<User> users);

	UserDto getByLogin(String login);

	int getLastUserId();

	boolean clearUserTable();
}