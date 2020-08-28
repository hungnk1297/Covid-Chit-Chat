package com.covid.chitchat.constant;

import lombok.Getter;

@Getter
public enum LogType {

    LOG_IN("I"),
    LOG_OUT("O");

    private String value;

    LogType(String value) {
        this.value = value;
    }

    public static LogType findByString(String value) {
        for (LogType logType : LogType.values()) {
            if (logType.getValue().equals(value))
                return logType;
        }
        return null;
    }
}
