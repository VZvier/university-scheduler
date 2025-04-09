package ua.foxminded.university.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.service.UserService;
import ua.foxminded.university.service.data.UserRole;

@Slf4j
@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public String getAllUsers(Model model) {
        model.addAttribute("pagePurpose", "User Management");
        model.addAttribute("jumbotronMessage", "Here you can check users list, remove users or add new!");
        model.addAttribute("userList", userService.getAllUsers());
        return "pages/users";
    }

    @GetMapping("/")
    public String getUserById(@RequestParam(name = "userId") int userId, Model model) {
        model.addAttribute("pagePurpose", "User Management");
        model.addAttribute("jumbotronMessage", "Here you can check users list, remove users or add new!");
        model.addAttribute("userList", userService.getUserById(userId));
        return "pages/users";
    }

    @GetMapping("/user-by-login")
    public String getUserByLogin(@RequestParam(name = "login") String login, Model model) {
        model.addAttribute("pagePurpose", "User Management");
        model.addAttribute("jumbotronMessage", "Here you can check users list, remove users or add new!");
        model.addAttribute("userList", userService.getByLogin(login.strip()));
        return "pages/users";
    }

    @GetMapping("/all-users-with-role")
    public String getAllUsersWithRole(@RequestParam(name = "role") UserRole role, Model model) {
        model.addAttribute("pagePurpose", "User Management");
        model.addAttribute("jumbotronMessage", "Here you can check users list, remove users or add new!");
        model.addAttribute("userList", userService.getUserByRole(role));
        return "pages/users";
    }

    @PostMapping("/removing-user/")
    public String remove(@RequestParam(name = "id") long id, Model model) {
        log.info("Method remove user - {}", id);
        userService.removeUser(id);
        model.addAttribute("pagePurpose", "User Management");
        model.addAttribute("jumbotronMessage", String.format("User removed %s!", id));
        model.addAttribute("userList", userService.getAllUsers());
        return "pages/users";
    }

    @PostMapping("/new-user/")
    public String addUser(@RequestParam("username") String login, @RequestParam("password") String password,
                          @RequestParam("role") String role, Model model) {
        log.info("Got user {}, {}, {}!", login, password, role);
        User user = User.builder()
                .id((long) (userService.getLastUserId() + 1))
                .login(login.strip()).password(password.strip())
                .role(UserRole.get(role))
                .build();
        userService.saveUser(user);
        model.addAttribute("pagePurpose", "User Management");
        model.addAttribute("jumbotronMessage", "New user " + user + " was added!");
        model.addAttribute("userList", userService.getAllUsers());
        return "pages/users";
    }
}