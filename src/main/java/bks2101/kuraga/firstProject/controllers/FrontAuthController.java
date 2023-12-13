package bks2101.kuraga.firstProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontAuthController {
    @GetMapping("/auth")
    public String AuthPage() {
        return "auth";
    }
}