package com.amr.shop.athj.auth_service_java.user.domain;

public class UserAuthException extends RuntimeException {
    public UserAuthException(String message) {
        super(message);
    }

    public UserAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
