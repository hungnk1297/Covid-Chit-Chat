package com.covid.chitchat.controller;

import com.covid.chitchat.model.request.UserRequestDTO;
import com.covid.chitchat.model.response.UserResponseDTO;
import com.covid.chitchat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Slf4j
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserResponseDTO> login(
            @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.login(userRequestDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Boolean> logout() {
        return new ResponseEntity<>(userService.logout(), HttpStatus.OK);
    }
}
