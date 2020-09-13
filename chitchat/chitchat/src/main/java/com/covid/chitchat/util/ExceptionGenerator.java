package com.covid.chitchat.util;

import com.covid.chitchat.exception.ApiError;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.covid.chitchat.constant.CommonConstant.ApiErrorStringConstant.*;
import static com.covid.chitchat.constant.CommonConstant.FieldNameConstant.USERNAME;

public class ExceptionGenerator {

    private ExceptionGenerator() {
    }

    public static ApiError duplicateUsername(String username) {
        return ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusMessage(DUPLICATED_USERNAME)
                .timestamp(LocalDateTime.now())
                .fieldName(USERNAME)
                .fieldValue(username)
                .build();
    }

    public static ApiError notFound(String entityName, String fieldName, Object fieldValue) {
        return ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusMessage(ENTITY_NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .entityName(entityName)
                .fieldName(fieldName)
                .fieldValue(fieldValue)
                .build();
    }

    public static ApiError invalidLogin() {
        return ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusMessage(USERNAME_OR_PASSWORD_INCORRECT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiError noUserLogin() {
        return ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusMessage(NO_USER_LOGGED_IN)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
