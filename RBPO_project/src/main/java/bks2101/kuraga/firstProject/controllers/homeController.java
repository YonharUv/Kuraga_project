package bks2101.kuraga.firstProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class homeController {
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("title", "Главная страница"); // "title" передаётся в шаблон и в нём, при обращении, вывыдется "Главная старница"
        return "home";
    }
}
