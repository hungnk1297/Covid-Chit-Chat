package com.covid.chitchat.util;

import com.covid.chitchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class Validator {

    private final UserRepository userRepository;

    //  Validate duplicate username when creating
    public boolean isDuplicateUsername(String username) {
        return userRepository.isDuplicateUsername(username);
    }
}
