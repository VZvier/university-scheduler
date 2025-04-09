package ua.foxminded.university.test.homepage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ua.foxminded.university.controller.HomePageController;
import ua.foxminded.university.mockuser.WithMockRoleAdmin;
import ua.foxminded.university.mockuser.WithMockRoleLecturer;
import ua.foxminded.university.mockuser.WithMockRoleStudent;

@WebMvcTest(HomePageController.class)
@AutoConfigureMockMvc(addFilters = false)
class HomePageControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockRoleAdmin
    void testGetHomePageWithMockRoleAdmin() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "HOME PAGE"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Please, select one of available options"))
                .andExpect(MockMvcResultMatchers.view().name("pages/home"));
    }

    @Test
    @WithMockRoleLecturer
    void testGetHomePageWithMockRoleLecturer() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "HOME PAGE"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Please, select one of available options"))
                .andExpect(MockMvcResultMatchers.view().name("pages/home"));
    }

    @Test
    @WithMockRoleStudent
    void testGetHomePageWithMockRoleStudent() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("pagePurpose"))
                .andExpect(MockMvcResultMatchers.model().attribute("pagePurpose", "HOME PAGE"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("jumbotronMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("jumbotronMessage",
                        "Please, select one of available options"))
                .andExpect(MockMvcResultMatchers.view().name("pages/home"));
    }

    @Test
    @WithAnonymousUser
    void testGetHomePageWithAnonymous() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/home"));
    }
}
