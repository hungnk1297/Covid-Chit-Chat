package com.covid.chitchat.config;

import com.covid.chitchat.constant.RoleType;
import com.covid.chitchat.service.ScreenCaptureService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ScreenCaptureConfig {

    private ScreenCaptureService screenCaptureService;

    private String username;

    private RoleType roleType;
}
