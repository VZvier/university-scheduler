package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.service.data.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDtoRepository extends JpaRepository<UserDto, Integer> {

    List<UserDto> findByRole(String role);

    Optional<UserDto> findByLogin(String login);

}
