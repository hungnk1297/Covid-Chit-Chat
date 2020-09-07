package com.covid.chitchat.service.impl;

import com.covid.chitchat.constant.CommonConstant;
import com.covid.chitchat.constant.CommonConstant.EntityNameConstant;
import com.covid.chitchat.constant.CommonConstant.FieldNameConstant;
import com.covid.chitchat.constant.LogType;
import com.covid.chitchat.entity.ActivityLog;
import com.covid.chitchat.entity.ScreenShot;
import com.covid.chitchat.entity.User;
import com.covid.chitchat.entity.injection.ScreenShotView;
import com.covid.chitchat.exception.CustomException;
import com.covid.chitchat.model.response.ActivityLogResponseDTO;
import com.covid.chitchat.model.response.ScreenShotResponseDTO;
import com.covid.chitchat.repository.ActivityLogRepository;
import com.covid.chitchat.repository.ScreenShotRepository;
import com.covid.chitchat.repository.UserRepository;
import com.covid.chitchat.service.ActivityLogService;
import com.covid.chitchat.util.ExceptionGenerator;
import com.covid.chitchat.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;
    private final ScreenShotRepository screenShotRepository;

    @Autowired
    @Qualifier("tempPath")
    private final Path tempPath;

    @Autowired
    ServletContext servletContext;

    @Transactional
    @Override
    public boolean logActivity(String username, LogType logType) {
        User user = userRepository.getByUsername(username);

        if (user == null)
            throw new CustomException(ExceptionGenerator.notFound(
                    EntityNameConstant.USER, FieldNameConstant.USERNAME, username));

        ActivityLog activity = ActivityLog.builder()
                .logType(logType)
                .user(user)
                .build();

        activityLogRepository.save(activity);
        return true;
    }

    @Override
    public List<ActivityLogResponseDTO> getAllLogs() {
        List<ActivityLog> activityLogResponseDTOList = activityLogRepository.findAll();
        return activityLogResponseDTOList.stream().map(this::toActivityLogResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<ScreenShotResponseDTO> getAllScreenShots() {
        List<ScreenShotView> screenShotViews = screenShotRepository.findAllScreenShot();
        return screenShotViews.stream().map(this::toScreenCapResponseDTO).collect(Collectors.toList());
    }

    @Override
    public Resource downloadScreenShot(Long screenShotID) {
        ScreenShot screenShot = screenShotRepository.getOne(screenShotID);
        if (screenShot == null)
            throw new CustomException(ExceptionGenerator.notFound(
                    EntityNameConstant.SCREEN_SHOT, FieldNameConstant.SCREEN_SHOT_ID, screenShotID));

        try {
            Path screenShotPath = tempPath.resolve(screenShot.getScreenShotName()).toAbsolutePath();
            File screenShotFile = new File(screenShotPath.toString());
            if (!screenShotFile.exists()) {
                Files.createFile(screenShotPath);
                OutputStream outputStream = new FileOutputStream(screenShotFile);
                outputStream.write(screenShot.getBinaryContent());
                outputStream.close();
            }
            return FileUtil.download(screenShotPath);
        } catch (Exception e) {
            log.error("Error when trying to download screen shot with id {}", screenShotID, e);
            return null;
        }
    }

    private ActivityLogResponseDTO toActivityLogResponseDTO(ActivityLog activityLog) {
        return ActivityLogResponseDTO.builder()
                .username(activityLog.getUser().getUsername())
                .logType(activityLog.getLogType().getDisplayValue())
                .logTime(activityLog.getCreatedOn().format(DateTimeFormatter.ofPattern(CommonConstant.FileConstant.DATE_TIME_PATTERN)))
                .build();
    }

    private ScreenShotResponseDTO toScreenCapResponseDTO(ScreenShotView screenShotView) {
        return ScreenShotResponseDTO.builder()
                .screenShotID(screenShotView.getScreenShotID())
                .username(screenShotView.getUsername())
                .screenShotName(screenShotView.getScreenShotName())
                .build();
    }
}
