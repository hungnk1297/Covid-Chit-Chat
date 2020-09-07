package com.covid.chitchat.service;

import com.covid.chitchat.constant.LogType;
import com.covid.chitchat.model.response.ActivityLogResponseDTO;
import com.covid.chitchat.model.response.ScreenShotResponseDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ActivityLogService {

    boolean logActivity(String username, LogType logType);

    List<ActivityLogResponseDTO> getAllLogs();

    List<ScreenShotResponseDTO> getAllScreenShots();

    Resource downloadScreenShot(Long screenShotID);
}
