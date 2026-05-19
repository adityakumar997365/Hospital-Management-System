package com.hms.HospitalManagementSystem.IndexController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // Renders the index.html page
    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This matches templates/login.html exactly
    }
    
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // Resolves to templates/register.html
    }
}
