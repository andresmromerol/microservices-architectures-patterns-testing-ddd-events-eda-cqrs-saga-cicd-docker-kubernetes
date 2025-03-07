package com.amr.shop.cmmj.common_java_context.services.auth;

public enum AuthNumericEnum {
    JWT_EXTRACT_INDEX(7);

    private final int value;

    AuthNumericEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
