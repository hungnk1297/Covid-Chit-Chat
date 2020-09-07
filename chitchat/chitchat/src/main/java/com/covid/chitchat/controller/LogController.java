package com.covid.chitchat.controller;

import com.covid.chitchat.constant.CommonConstant;
import com.covid.chitchat.controller.authentication.AdminRequiredController;
import com.covid.chitchat.model.response.ActivityLogResponseDTO;
import com.covid.chitchat.model.response.ScreenShotResponseDTO;
import com.covid.chitchat.service.ActivityLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.covid.chitchat.constant.CommonConstant.FileConstant.*;

@Controller
@RequestMapping(path = "/log")
@Slf4j
@AllArgsConstructor
public class LogController extends AdminRequiredController {

    private final ActivityLogService activityLogService;

    @GetMapping("/activity")
    public String logActivity(Model model) {
        if (isLoggedIn()) {
            if (isAdmin()) {
                List<ActivityLogResponseDTO> activityLogResponseDTOList = activityLogService.getAllLogs();
                model.addAttribute(CommonConstant.AttributeConstant.ACTIVITY_LOGS, activityLogResponseDTOList);
                return "log-activity";
            } else
                return "home";
        } else
            return "login";
    }

    @GetMapping("/screen-cap")
    public String logScreenCap(Model model) {
        if (isLoggedIn()) {
            if (isAdmin()) {
                List<ScreenShotResponseDTO> screenShotResponseDTOS = activityLogService.getAllScreenShots();
                model.addAttribute(CommonConstant.AttributeConstant.SCREEN_CAPS, screenShotResponseDTOS);
                return "log-screen-cap";
            } else
                return "home";
        } else
            return "login";
    }

    @RequestMapping(value = "/screen-cap/download", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@Param(value = "id") Long id, HttpServletRequest request) {
        if (isLoggedIn() && isAdmin()) {
            Resource resource = activityLogService.downloadScreenShot(id);
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (Exception e) {
                log.error("Could not get the content type!");
            }
            contentType = contentType != null ? contentType : DEFAULT_CONTENT_TYPE;

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT_FILENAME + resource.getFilename() + KEY_SLASH)
                    .body(resource);
        }
        return null;
    }
}
