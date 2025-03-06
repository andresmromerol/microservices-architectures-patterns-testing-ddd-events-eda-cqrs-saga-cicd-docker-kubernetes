package com.amr.shop.athj.auth_service_java.user_vw.domain;

import com.amr.shop.cmmj.common_java_context.shared.exception.DomainException;

public class UserViewAuthDomainException extends DomainException {
    public UserViewAuthDomainException(String message) {
        super(message);
    }

    public UserViewAuthDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
