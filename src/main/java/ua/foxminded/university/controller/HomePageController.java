package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

	@GetMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("pagePurpose", "HOME PAGE");
		model.addAttribute("jumbotronMessage", "Please, select one of available options");
		return "pages/home";
	}
}
