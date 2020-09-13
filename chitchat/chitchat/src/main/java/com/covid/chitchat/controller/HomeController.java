package com.covid.chitchat.controller;

import com.covid.chitchat.controller.authentication.AdminRequiredController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends AdminRequiredController {

    @GetMapping("/")
    public String index(Model model) {
        return isLoggedIn() ? "home" : "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return isLoggedIn() ? "home" : "index";
    }
}
