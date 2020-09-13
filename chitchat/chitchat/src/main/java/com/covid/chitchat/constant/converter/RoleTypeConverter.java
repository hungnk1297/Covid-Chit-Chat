package com.covid.chitchat.constant.converter;

import com.covid.chitchat.constant.RoleType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleTypeConverter implements AttributeConverter<RoleType, String> {

    @Override
    public String convertToDatabaseColumn(RoleType roleType) {
        return roleType.getValue();
    }

    @Override
    public RoleType convertToEntityAttribute(String s) {
        return RoleType.findByString(s);
    }
}
