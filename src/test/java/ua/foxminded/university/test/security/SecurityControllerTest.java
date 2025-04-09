package ua.foxminded.university.test.security;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.configuration.DefaultSecurityConfig;
import ua.foxminded.university.controller.SecurityController;
import ua.foxminded.university.security.CustomAuthenticationProvider;
import ua.foxminded.university.service.data.UserRole;
import ua.foxminded.university.service.impl.UserServiceImpl;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(SecurityController.class)
class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomAuthenticationProvider authProvider;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @MockBean
    private UserServiceImpl userService;

    @Test
    void testGetLoginPage() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/login"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Login page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Please, authorize! If you haven't account, please register!"));
    }

    @Test
    void testPostLogin() throws Exception {
        String username = "test";
        String password = "test";
        UserDetails user = createDetails();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        Mockito.when(authProvider
                .authenticate(new UsernamePasswordAuthenticationToken(username, password))).thenReturn(auth);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/login?username=test&password=test"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    @Test
    void testOpenRegistrationPage() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/registration"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/registration"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "Registration Page"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "If you haven't account, please register!"));
    }

    @Test
    void testPostRegistration() throws Exception {
        Mockito.when(userService.getLastUserId()).thenReturn(0);
        Mockito.when(userService.saveUser(createUser())).thenReturn(true);
        Authentication auth = new UsernamePasswordAuthenticationToken(createDetails(),
                createDetails().getPassword(), createDetails().getAuthorities());
        Mockito.when(authProvider.authenticate(
                new UsernamePasswordAuthenticationToken("test", "test"))).thenReturn(auth);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/registration?login=test&password=test&confirmPassword=test"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.status().isMovedTemporarily())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    // Data for tests
    private UserDetails createDetails(){
        return User.builder()
                .username("test")
                .password(encoder.encode("test"))
                .authorities("ROLE_" + UserRole.STUDENT.getName())
                .build();
    }

    private ua.foxminded.university.entity.User createUser(){
        return ua.foxminded.university.entity.User.builder()
                .id((long)1)
                .login("test")
                .password("test")
                .confirmPassword("test")
                .role(UserRole.STUDENT).build();
    }
}
