package ua.foxminded.university.mockuser;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.testcontainers.shaded.org.checkerframework.checker.calledmethods.qual.CalledMethods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(value = "TestLecturer", roles = "LECTURER")
public @interface WithMockRoleLecturer {
}
