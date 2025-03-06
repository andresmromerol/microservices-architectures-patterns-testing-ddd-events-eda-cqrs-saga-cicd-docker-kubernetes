package com.amr.shop.athj.auth_service_java.user.domain;

public class UserAuthEmailAlreadyExistsException extends UserAuthException {

    public static final String EMAIL_ALREADY_EXISTS_S = "Email already exists %s";

    public UserAuthEmailAlreadyExistsException(String email) {
        super(String.format(EMAIL_ALREADY_EXISTS_S, email));
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
