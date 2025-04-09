package ua.foxminded.university.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.security.CustomAuthenticationProvider;
import ua.foxminded.university.service.UserService;
import ua.foxminded.university.service.data.UserRole;

@Slf4j
@Controller
public class SecurityController {

    private final UserService userService;

    private final CustomAuthenticationProvider provider;

    public SecurityController(UserService service, CustomAuthenticationProvider provider) {
        this.userService = service;
        this.provider = provider;
    }


    @GetMapping("/login")
    public String authPage(Model model) {
        model.addAttribute("pagePurpose", "Login page");
        model.addAttribute("jumbotronMessage", "Please, authorize! If you haven't account, please register!");
        return "pages/login";
    }

    @PostMapping("/login")
    public String auth(@RequestParam("username") String username,
                       @RequestParam("password") String password, Model model, HttpServletRequest request)
    {
        log.info("Method 'auth' receive username - {}, and password - {}", username, password);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            model.addAttribute("message", "Invalid username or password");
        } else {
            Authentication auth = provider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);
            model.addAttribute("message", "Welcome back user - " + username + "!");
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            log.info("Is {} authenticated - {}!", username, context.getAuthentication().isAuthenticated());
            return "redirect:/";
        }
        model.addAttribute("pagePurpose", "Login page");
        model.addAttribute("jumbotronMessage", "If you haven't account, please register!");
        return "pages/login";
    }

    @GetMapping("/registration")
    public String openRegistrationPage(Model model) {
        model.addAttribute("pagePurpose", "Registration Page");
        model.addAttribute("jumbotronMessage", "If you haven't account, please register!");
        return "pages/registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam("login") String login, @RequestParam("password") String password,
                @RequestParam("confirmPassword") String confirmPassword, Model model, HttpServletRequest request) {
        log.info("Registration got user: username - {}, password - {}, password confirm - {}", login, password, confirmPassword);
        User user = User.builder().id((long) (userService.getLastUserId() + 1)).login(login).password(password)
                .confirmPassword(password).role(UserRole.STUDENT).build();
        model.addAttribute("pagePurpose", "Registration Page");
        if (user.isEmpty()) {
            model.addAttribute("jumbotronMessage", "Please, register!");
            model.addAttribute("error", "Fields cannot be empty!");
            return "pages/registration";
        } else if (userService.getByLogin(user.getLogin()) != null) {
            model.addAttribute("jumbotronMessage", "Please, register!");
            model.addAttribute("error", "User with login "
                    + user.getLogin() + " already exists!");
            return "pages/registration";
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("jumbotronMessage", "Please, register!");
            model.addAttribute("error", "Passwords mismatched!");
            return "pages/registration";
        } else if (userService.saveUser(user)) {
            Authentication auth = provider.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            model.addAttribute("pagePurpose", "Welcome user " + user.getLogin() + "!");
            model.addAttribute("jumbotronMessage", "New user " + user.getLogin() + " was created!");
            log.info("Is {} authenticated - {}!", user.getLogin(), context.getAuthentication().isAuthenticated());
            return "redirect:/";
        } else {
            model.addAttribute("jumbotronMessage", "Please, register!");
            model.addAttribute("error", "Unknown error! Registration failed!");
            return "pages/registration";
        }
    }
}
