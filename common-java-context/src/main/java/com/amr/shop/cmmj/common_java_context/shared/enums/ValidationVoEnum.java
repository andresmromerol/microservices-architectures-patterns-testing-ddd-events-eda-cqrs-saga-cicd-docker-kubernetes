package com.amr.shop.cmmj.common_java_context.shared.enums;

public enum ValidationVoEnum {
    EMAIL_ERROR("Email is not valid"),
    PASSWORD_MINIMUM_LENGTH_ERROR("");

    private final String message;

    ValidationVoEnum(String message) {

        this.message = message;
    }

    public String getMessage() {

        return message;
    }
}
