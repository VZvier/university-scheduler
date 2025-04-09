package ua.foxminded.university.test.admin;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.university.configuration.DefaultSecurityConfig;
import ua.foxminded.university.controller.AdminController;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStudent;
import ua.foxminded.university.service.data.UserRole;
import ua.foxminded.university.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

@Import(DefaultSecurityConfig.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;


    @Test
    @WithMockRoleAdmin
    void getAllUsersForRoleAdmin() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(getUsers());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/users/all-users"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "User Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Here you can check users list, remove users or add new!"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("userList"))
                .andExpect(MockMvcResultMatchers.model().attribute("userList", getUsers()));
    }

    @Test
    @WithMockRoleLecturer
    void getAllUsersForRoleLecturer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/all-users"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleStudent
    void getAllUsersForRoleStudent() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/all-users"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void getAllUsersWithAnonymous() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/all-users"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockRoleAdmin
    void removeWithMockRoleAdmin() throws Exception {
        Mockito.when(userService.removeUser(1L)).thenReturn(true);
        Mockito.when(userService.getAllUsers()).thenReturn(getUsers());

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/removing-user/?id=1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "User Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "User removed 1!"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("userList"))
                .andExpect(MockMvcResultMatchers.model().attribute("userList", getUsers()));
    }

    @Test
    @WithMockRoleStudent
    void removeWithMockRoleStudent() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/remove-user/?id=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockRoleLecturer
    void removeWithMockRoleLecturer() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/remove-user/?id=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    void removeWithAnonymous() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/remove-user/?id=1"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockRoleAdmin
    void addWithMockRoleAdmin() throws Exception {
        Mockito.when(userService.getLastUserId()).thenReturn(0);
        User user = User.builder().id(1L).login("test").password("test").role(UserRole.STUDENT).build();
        Mockito.when(userService.saveUser(user)).thenReturn(true);
        Mockito.when(userService.getAllUsers()).thenReturn(getUsers());

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/new-user/?username=test&password=test&role=STUDENT"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "User Management"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "New user " + user + " was added!"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("userList"))
                .andExpect(MockMvcResultMatchers.model().attribute("userList", getUsers()));

    }

    @Test
    @WithMockRoleLecturer
    void addWithMockRoleLecturer() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/add-user/?username=test&password=test&role=STUDENT"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockRoleStudent
    void addWithMockRoleStudent() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/add-user/?username=test&password=test&role=STUDENT"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    void addWithAnonymous() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/add-user/?username=test&password=test&role=STUDENT"))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    private List<UserDto> getUsers(){
        UserDto user1 = new UserDto(1, "test1", "test", UserRole.STUDENT.getName());
        UserDto user2 = new UserDto(2, "test2", "test", UserRole.LECTURER.getName());
        UserDto user3 = new UserDto(3, "test3", "test", UserRole.ADMIN.getName());
        return Arrays.asList(user1, user2, user3);
    }
}
