package com.covid.chitchat.util;

import com.covid.chitchat.exception.CustomException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.covid.chitchat.constant.CommonConstant.FileConstant.FILE;
import static com.covid.chitchat.constant.CommonConstant.FileConstant.URL;


public class FileUtil {
    public static Resource download(Path filePath) throws IOException {
        if (Files.notExists(filePath)) {
            throw new CustomException(ExceptionGenerator.notFound(FILE, URL, filePath.toString()));
        }
        return new UrlResource(filePath.toUri());
    }
}
