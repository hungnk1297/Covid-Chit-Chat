package com.covid.chitchat.constant;

import lombok.Getter;

@Getter
public enum LogType {

    LOG_IN("I", "Log In"),
    LOG_OUT("O", "Log Out");

    private String value;
    private String displayValue;

    LogType(String value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public static LogType findByString(String value) {
        for (LogType logType : LogType.values()) {
            if (logType.getValue().equals(value))
                return logType;
        }
        return null;
    }
}
