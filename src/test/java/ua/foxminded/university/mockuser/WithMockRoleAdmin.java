package ua.foxminded.university.mockuser;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(value = "TestAdmin", roles = "ADMIN")
public @interface WithMockRoleAdmin {
}
