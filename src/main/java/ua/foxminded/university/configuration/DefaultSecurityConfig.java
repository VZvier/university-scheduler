package ua.foxminded.university.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.foxminded.university.service.data.UserRole;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "/login", "/registration", "/registration/**")
                        .permitAll()

                        .requestMatchers("/common/courses/all")
                        .permitAll()

                        .requestMatchers("/common/**", "/common/*")
                        .hasAnyRole(UserRole.STUDENT.getName(), UserRole.LECTURER.getName(),
                                UserRole.STAFF.getName(), UserRole.ADMIN.getName())

                        .requestMatchers("/lecturer/**", "/lecturers/**")
                        .hasAnyRole(UserRole.LECTURER.getName(), UserRole.STAFF.getName(), UserRole.ADMIN.getName())

                        .requestMatchers("/staff/**")
                        .hasAnyRole(UserRole.STAFF.getName(), UserRole.ADMIN.getName())

                        .requestMatchers("/admin/**")
                        .hasRole(UserRole.ADMIN.getName()))

                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}