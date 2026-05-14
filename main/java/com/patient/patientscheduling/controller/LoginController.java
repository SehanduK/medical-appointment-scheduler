package com.patient.patientscheduling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/verify-admin")
    public String verifyAdmin(@RequestParam String username,
                              @RequestParam String password,
                              Model model) {

        if ("admin".equals(username) && "123".equals(password)) {
            // Change this line to redirect to the dashboard mapping
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid Credentials. Access Denied.");
            return "login";
        }
    }
}