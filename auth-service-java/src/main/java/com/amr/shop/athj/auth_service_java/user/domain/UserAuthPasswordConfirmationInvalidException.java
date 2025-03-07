package com.amr.shop.athj.auth_service_java.user.domain;

public class UserAuthPasswordConfirmationInvalidException extends UserAuthException {

    public static final String PASSWORD_CONFIRMATION_INVALID = "Password confirmation does not match";

    public UserAuthPasswordConfirmationInvalidException() {
        super(PASSWORD_CONFIRMATION_INVALID);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
