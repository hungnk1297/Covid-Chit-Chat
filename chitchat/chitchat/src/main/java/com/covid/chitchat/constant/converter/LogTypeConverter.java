package com.covid.chitchat.constant.converter;

import com.covid.chitchat.constant.LogType;

import javax.persistence.AttributeConverter;

public class LogTypeConverter implements AttributeConverter<LogType, String> {

    @Override
    public String convertToDatabaseColumn(LogType logType) {
        return logType.getValue();
    }

    @Override
    public LogType convertToEntityAttribute(String s) {
        return LogType.findByString(s);
    }
}
