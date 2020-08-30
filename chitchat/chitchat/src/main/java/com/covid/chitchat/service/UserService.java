package com.covid.chitchat.service;

import com.covid.chitchat.model.request.UserRequestDTO;
import com.covid.chitchat.model.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO login(UserRequestDTO userRequestDTO);

    Boolean logout();
}
