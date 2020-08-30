package com.covid.chitchat.constant;

public class CommonConstant {

    public static class ApiErrorStringConstant{
        public static final String DUPLICATED_USERNAME = "The username is existed!";
        public static final String ENTITY_NOT_FOUND = "The Entity is NOT found!";
        public static final String USERNAME_OR_PASSWORD_INCORRECT = "Username or Password is incorrect!";
        public static final String NO_USER_LOGGED_IN = "No User logging in!";
        public static final String UNAUTHORIZED = "The Employee Token is Unauthorized!";
        public static final String FILE_UPLOAD_FAILED = "Failed when uploading file!";
        public static final String NO_READ_PERMISSION = "You have no read permission for the file!";
        public static final String NOT_OWNER_OF_THE_FILE = "You are not the owner of the file!";
    }

    public static class FieldNameConstant{
        public static final String USERNAME = "username";
        public static final String EMPLOYEE_ID = "employeeID";
        public static final String FILE_ID = "fileID";
        public static final String URL = "url";
    }

    public static class EntityNameConstant{
        public static final String USER = "User";
        public static final String SCREEN_SHOT = "Screen Shot";
        public static final String ACTIVITY_LOG = "Activity Log";
    }

    public static class FileConstant{
        public static final String DOT = ".";
        public static final String UNDERSCORE = "_";
        public static final String JPG_FORMAT = "jpg";
        public static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh-mm-ss a";
        public static final String ZIP_EXTENSION = ".zip";
        public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
        public static final String ATTACHMENT_FILENAME = "attachment; filename=\"";
        public static final String KEY_SLASH = "\"";
        public static final String COMPRESSES_FILE = "Compressed-files";
    }
}
