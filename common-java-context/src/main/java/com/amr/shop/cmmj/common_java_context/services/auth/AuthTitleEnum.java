package com.amr.shop.cmmj.common_java_context.services.auth;

public enum AuthTitleEnum {
    BEARER("Bearer "),

    AUTHORIZATION_HEADER("Authorization"),
    ROLE("ROLE_");

    private final String value;

    AuthTitleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return value.length();
    }
}
