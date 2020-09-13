package com.covid.chitchat.controller;

import com.covid.chitchat.constant.LogType;
import com.covid.chitchat.model.request.UserRequestDTO;
import com.covid.chitchat.model.response.UserResponseDTO;
import com.covid.chitchat.service.ActivityLogService;
import com.covid.chitchat.service.UserService;
import com.covid.chitchat.util.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.covid.chitchat.constant.CommonConstant.AttributeConstant.*;


@Controller
@RequestMapping(path = "/user")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ActivityLogService activityLogService;

    // Thymeleaf
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("userRequestDTO") UserRequestDTO userRequestDTO) {
        UserResponseDTO userLoggedIn = userService.login(userRequestDTO);
        if (userLoggedIn != null) {
            HttpSession session = Validator.getSession();
            session.setAttribute(USER_LOGGED_IN, userLoggedIn.getUsername());
            session.setAttribute(ROLE, userLoggedIn.getRole());
            //  Log
            activityLogService.logActivity(userLoggedIn.getUsername(), LogType.LOG_IN);
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRequestDTO", new UserRequestDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("userRequestDTO") UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        if (createdUser != null) {
            model.addAttribute(CREATED_SUCCESS, true);
            return "login";
        } else {
            model.addAttribute(CREATED_SUCCESS, false);
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        HttpSession session = Validator.getSession();
        String username = (String) session.getAttribute(USER_LOGGED_IN);
        userService.clearSession();
        userService.logout();
        activityLogService.logActivity(username, LogType.LOG_OUT);
        return "redirect:/";
    }
}
