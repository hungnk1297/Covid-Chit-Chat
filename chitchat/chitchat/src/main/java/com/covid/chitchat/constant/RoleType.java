package com.covid.chitchat.constant;

import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN("A"),
    USER("U");

    private String value;

    RoleType(String value) {
        this.value = value;
    }

    public static RoleType findByString(String value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.getValue().equals(value))
                return roleType;
        }
        return null;
    }
}
