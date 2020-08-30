package com.covid.chitchat.service.impl;

import com.covid.chitchat.constant.CommonConstant.EntityNameConstant;
import com.covid.chitchat.constant.CommonConstant.FieldNameConstant;
import com.covid.chitchat.constant.RoleType;
import com.covid.chitchat.entity.User;
import com.covid.chitchat.exception.CustomException;
import com.covid.chitchat.model.request.UserRequestDTO;
import com.covid.chitchat.model.response.UserResponseDTO;
import com.covid.chitchat.repository.UserRepository;
import com.covid.chitchat.service.ScreenCaptureService;
import com.covid.chitchat.service.UserService;
import com.covid.chitchat.util.CommonUtil;
import com.covid.chitchat.util.ExceptionGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.nio.file.Path;

@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CommonUtil commonUtil;

    @Autowired
    private final ScreenCaptureService screenCaptureService;

    @Qualifier("screenShotPath")
    private final Path capturePath;

    @Autowired
    ServletContext servletContext;

    @Transactional
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (commonUtil.isDuplicateUsername(userRequestDTO.getUsername()))
            throw new CustomException(ExceptionGenerator.duplicateUsername(userRequestDTO.getUsername()));

        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .password(commonUtil.generateHashPassword(userRequestDTO.getUsername(), userRequestDTO.getPassword()))
                .role(userRequestDTO.getRoleType() != null ? RoleType.findByString(userRequestDTO.getRoleType()) : RoleType.USER)
                .build();

        userRepository.save(user);

        return toUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO login(UserRequestDTO userRequestDTO) {
        User user = userRepository.getByUsername(userRequestDTO.getUsername());

        if (user == null)
            throw new CustomException(ExceptionGenerator.notFound(
                    EntityNameConstant.USER, FieldNameConstant.USERNAME, userRequestDTO.getUsername()));

        //  Compare hashed password from request vs hashed password in DB
        String tryHashPassword = commonUtil.generateHashPassword(userRequestDTO.getUsername(), userRequestDTO.getPassword());
        if (!tryHashPassword.equals(user.getPassword()))
            throw new CustomException(ExceptionGenerator.invalidLogin());
        else {
            //  Write username and role to Session and start capturing screen every 30 seconds
            servletContext.setAttribute("user", user.getUsername());
            servletContext.setAttribute("role", user.getRole().getValue());

            try {
                screenCaptureService.setUsername(user.getUsername());
                screenCaptureService.setCapturePath(capturePath);
                screenCaptureService.refresh();
                log.info("Screen capture of current logged in User {} start now!", user.getUsername());
                Thread thread = new Thread(screenCaptureService);
                thread.start();
            } catch (Exception e) {
                log.error("Error when trying to capture screen! ", e);
            }
            return toUserResponseDTO(user);
        }
    }

    @Override
    public Boolean logout() {
        try {
            String username = (String) servletContext.getAttribute("user");
            log.info("Stopping screenshot service for user {}", username);
            screenCaptureService.terminate();
            servletContext.setAttribute("user", null);
            servletContext.setAttribute("role", null);
            return true;
        } catch (Exception e) {
            log.error("Error when trying to log out", e);
            return false;
        }
    }

    private UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .userID(user.getUserID())
                .username(user.getUsername())
                .role(user.getRole().getValue())
                .createdOn(user.getCreatedOn())
                .build();
    }


}
