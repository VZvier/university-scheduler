package ua.foxminded.university.mockuser;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(value = "TestStudent", roles = "STUDENT", username = "TestStudent", password = "test")
public @interface WithMockRoleStudent {
}