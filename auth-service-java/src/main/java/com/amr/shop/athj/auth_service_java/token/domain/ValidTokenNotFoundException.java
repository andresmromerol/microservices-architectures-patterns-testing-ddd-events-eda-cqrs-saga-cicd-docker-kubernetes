package com.amr.shop.athj.auth_service_java.token.domain;

import java.util.UUID;

public class ValidTokenNotFoundException extends TokenException {

    public static final String VALID_TOKENS_NOT_FOUND = "Failed to find all valid tokens for user: %s";

    public ValidTokenNotFoundException(UUID user) {
        super(String.format(VALID_TOKENS_NOT_FOUND, user));
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
