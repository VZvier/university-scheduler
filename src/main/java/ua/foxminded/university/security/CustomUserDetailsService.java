package ua.foxminded.university.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService service;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = service.getByLogin(username);
        if (user != null) {
            log.info("User {} loaded!", username);
            return service.convertUserDtoToUserDetails(user);
        } else{
            log.error("Doesn't - '{}'", username);
            return null;
        }
    }
}
