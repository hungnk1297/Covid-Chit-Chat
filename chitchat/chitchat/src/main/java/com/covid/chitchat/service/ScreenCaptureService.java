package com.covid.chitchat.service;

import com.covid.chitchat.constant.CommonConstant;
import com.covid.chitchat.entity.ScreenShot;
import com.covid.chitchat.entity.User;
import com.covid.chitchat.exception.CustomException;
import com.covid.chitchat.repository.ScreenShotRepository;
import com.covid.chitchat.repository.UserRepository;
import com.covid.chitchat.util.ExceptionGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.covid.chitchat.constant.CommonConstant.FileConstant.*;


@Slf4j
@Component
@Getter
@Setter
public class ScreenCaptureService implements Runnable {

    private final ScreenShotRepository screenShotRepository;
    private final UserRepository userRepository;

    private volatile boolean running = true;
    private final int SCREEN_SHOT_DELAY = 1000 * 30;

    private String username;

    private Path capturePath;

    public ScreenCaptureService(ScreenShotRepository screenShotRepository, UserRepository userRepository) {
        this.screenShotRepository = screenShotRepository;
        this.userRepository = userRepository;
    }

    public void terminate() {
        running = false;
    }

    public void refresh() {
        running = true;
    }

    @Override
    public void run() {
        User user = userRepository.getByUsername(this.username);
        if (user == null)
            throw new CustomException(ExceptionGenerator.notFound(
                    CommonConstant.EntityNameConstant.USER, CommonConstant.FieldNameConstant.USERNAME, this.username));

        while (running) {
            try {
                // Capture screen every 30s
                Path savingPath = captureScreen(this.username, this.capturePath);
                FileInputStream fin = new FileInputStream(savingPath.toString());

                ScreenShot newScreenShot = ScreenShot.builder()
                        .user(user)
                        .screenShotName(capturePath.toAbsolutePath().resolve(username).relativize(savingPath).toString())
                        .url(capturePath.toAbsolutePath().relativize(savingPath).toString())
                        .binaryContent(fin.readAllBytes())
                        .build();
                screenShotRepository.save(newScreenShot);

                log.info("Screen capture of {} saved!", this.username);
                log.info("Sleep for {} seconds", SCREEN_SHOT_DELAY / 1000);
                Thread.sleep(SCREEN_SHOT_DELAY);
            } catch (InterruptedException | FileNotFoundException e) {
                log.error("Exception", e);
                running = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Path captureScreen(String username, Path savingPath) {
        if (username == null)
            throw new CustomException(ExceptionGenerator.noUserLogin());

        //  Capture full screen
        try {
            System.setProperty("java.awt.headless", "false");
            Robot robot = new Robot();

            savingPath = savingPath.resolve(username);
            if (!savingPath.toFile().exists())
                Files.createDirectories(savingPath);

            String fileName = new StringBuilder()
                    .append(username).append(UNDERSCORE)
                    .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))
                    .append(DOT).append(JPG_FORMAT)
                    .toString();
            savingPath = savingPath.resolve(fileName).toAbsolutePath();

            Files.createFile(savingPath);
            File screenShotFile = new File(savingPath.toString());

            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, JPG_FORMAT, screenShotFile);
            return savingPath;
        } catch (AWTException | IOException ex) {
            log.error("Error while capturing screen shot of user {}", username, ex);
        }
        return null;
    }


}
