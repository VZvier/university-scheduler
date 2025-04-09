package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.repository.UserDtoRepository;
import ua.foxminded.university.repository.UserRepository;
import ua.foxminded.university.service.UserService;
import ua.foxminded.university.service.data.UserRole;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserDtoRepository userDtoRepository;

	private final PasswordEncoder encoder;

	public UserServiceImpl(UserRepository userRepository, UserDtoRepository userDtoRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.userDtoRepository = userDtoRepository;
		this.encoder = encoder;
	}

	@Override
	public UserDto getByLogin(String login) {
		Optional<UserDto> optionalUser = userDtoRepository.findByLogin(login);
		if (optionalUser.isPresent()) {
			UserDto user = optionalUser.get();
			log.info("User {} found.", login);
			String pass = user.getPassword();
			user.setPassword(pass);
			return user;
		} else {
			log.error("No such user found!");
			return null;
		}
	}

	@Override
	public int getLastUserId() {
		return userRepository.getLastUserId();
	}


	@Override
	public List<UserDetails> convertUserDtoToUserDetails(List<UserDto> users) {
		List<UserDetails> convertedUsers = new LinkedList<>();
		for (UserDto user : users) {
			convertedUsers.add(org.springframework.security.core.userdetails
					.User.builder()
					.username(user.getLogin().trim())
					.password("{bcrypt}" + user.getPassword().trim())
					.roles(user.getRole().trim())
					.build());
		}
		log.info("Users were converted to Spring UserDetails - {}!", convertedUsers.size());
		return convertedUsers;
	}

	@Override
	public UserDetails convertUserDtoToUserDetails(UserDto user) {
		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
				.username(user.getLogin().strip())
				.password(user.getPassword().strip())
				.authorities("ROLE_" + user.getRole().strip())
				.build();
		log.info("User converted - {}, {} to Spring UserDetails!", userDetails.getUsername(), userDetails.getAuthorities());
		return userDetails;
	}

	@Override
	public List<UserDto> getAllUsers() {
		log.info("Start get users from database!");
		List<UserDto> users = userDtoRepository.findAll();
		Collections.sort(users);
		return users;
	}

	@Override
	public List<UserDto> getUserByRole(UserRole role) {
		log.info("Get all users with role - {}", role);
		List<UserDto> users = userDtoRepository.findByRole(role.getName());
		Collections.sort(users);
		return users;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		log.info("Got user №{} from database!", userId);
		return userDtoRepository.findById(userId).orElse(null);
	}

	@Override
	public boolean removeUser(Long userId) {
		log.info("User {} was removed from database!", userId);
		userRepository.deleteById(userId);
		boolean isUserRemoved = userRepository.findById(userId).isEmpty();
		return isUserRemoved;
	}

	@Override
	public boolean saveUser(User user) {
		user.setEncodedPassword(encoder.encode(user.getPassword()));
		log.info("User will be saved {}, {}, {}, {}", user.getId(),
				user.getLogin(), user.getEncodedPassword(), user.getRole());
		userRepository.save(user);
		return true;
	}

	@Override
	public Integer saveUserList(List<User> users) {
		log.info("Start fill table usr with {} users! First user is {}", users.size(), users.get(0));
		return userRepository.saveAll(users).size();
	}


	@Override
	public boolean clearUserTable() {
		userRepository.deleteAll();
		return true;
	}
}
