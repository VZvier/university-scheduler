package ua.foxminded.university.test.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.repository.UserDtoRepository;
import ua.foxminded.university.repository.UserRepository;
import ua.foxminded.university.service.data.UserRole;
import ua.foxminded.university.service.impl.UserServiceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoRepository userDtoRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetAllUsers() {
        List<UserDto> expectedResult = createUserDtoList();

        when(userDtoRepository.findAll()).thenReturn(expectedResult);
        List<UserDto> actualResult = userService.getAllUsers();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetUserById() {
        UserDto expectedResult = createUserDtoList().get(1);

        when(userDtoRepository.findById(1)).thenReturn(Optional.of(expectedResult));
        UserDto actualResult = userService.getUserById(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetByLogin() {
        UserDto expectedResult = createUserDtoList().get(1);

        when(userDtoRepository.findByLogin("test1")).thenReturn(Optional.of(expectedResult));
        UserDto actualResult = userService.getByLogin("test1");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetLastUserId() {
        int expectedResult = 1;

        when(userRepository.getLastUserId()).thenReturn(expectedResult);
        int actualResult = userService.getLastUserId();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testConvertUserDtoListToUserDetails() {
        List<UserDto> testing = createUserDtoList();

        UserDetails expectedResult = org.springframework.security.core.userdetails.User
                .withUsername("testUser1").password(encoder.encode("test1")).authorities(UserRole.LECTURER.getName()).build();
        UserDetails actualResult = userService.convertUserDtoToUserDetails(testing.get(1));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testRemoveUser() {
        doNothing().when(userRepository).deleteById((long) 1);
        boolean expectedResult = true;

        boolean actualResult = userService.removeUser(1L);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveUser() {
        User user = createUserList().get(1);
        when(userRepository.save(user)).thenReturn(user);
        boolean expectedResult = true;
        UserServiceImpl uService = new UserServiceImpl(userRepository, userDtoRepository, encoder);

        boolean actualResult = uService.saveUser(user);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveUserList() {
        List<User> users = createUserList();
        int expectedResult = users.size();

        when(userRepository.saveAll(users)).thenReturn(users);
        int actualResult = userService.saveUserList(users);

        assertEquals(expectedResult, actualResult);
    }

    // Data for tests
    private List<User> createUserList(){
        List<User> users = new LinkedList<>();
        List<UserRole> roles = new LinkedList<>();
        roles.add(UserRole.STUDENT);
        roles.add(UserRole.LECTURER);
        roles.add(UserRole.ADMIN);
        int role;
        for (int i = 0; i < 10; i++) {
            role = ((i % 3) == 0 )? 0: i % 3;
            users.add(User.builder()
                    .id((long)(i + 1))
                    .login("testUser" + i)
                    .password("test" + i)
                    .confirmPassword("test" + i)
                    .encodedPassword(encoder.encode("test" + i))
                    .role(roles.get((role)))
                    .build());
        }
        return users;
    }

    private List<UserDto> createUserDtoList(){
        List<UserDto> users = new LinkedList<>();
        List<UserRole> roles = new LinkedList<>();
        roles.add(UserRole.STUDENT);
        roles.add(UserRole.LECTURER);
        roles.add(UserRole.ADMIN);
        int role;
        for (int i = 0; i < 10; i++) {
            role = ((i % 3) == 0 )? 0: i % 3;
            users.add(new UserDto((i + 1), ("testUser" + i), ("test" + i), roles.get(role).getName()));
        }
        return users;
    }
}

