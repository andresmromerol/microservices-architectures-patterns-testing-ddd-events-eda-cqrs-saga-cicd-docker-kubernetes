package com.amr.shop.athj.auth_service_java.shared.infrastructure.exception;

public final class AuthBusinessCode {
    public static final String AUTH_ERROR = "AUTH_00";
    public static final String VALIDATION_ERROR = "AUTH_10";
    public static final String EMAIL_ALREADY_EXISTS = "AUTH_20";
    public static final String AUTH_UNAUTHORIZED = "AUTH_30";
    public static final String AUTH_NOT_FOUND = "AUTH_40";
    public static final String AUTH_TOKEN_NOT_FOUND = "AUTH_50";
    public static final String AUTH_TOKEN_REVOCATION_FAILED = "AUTH_60";
    public static final String AUTH_TOKEN_SAVE_FAILED = "AUTH_70";
    public static final String AUTH_PASSWORD_CONFIRMATION_INVALID = "AUTH_80";
    public static final String AUTH_NEW_PASSWORD_CONFIRMATION_INVALID = "AUTH_90";

    private AuthBusinessCode() {}
}
