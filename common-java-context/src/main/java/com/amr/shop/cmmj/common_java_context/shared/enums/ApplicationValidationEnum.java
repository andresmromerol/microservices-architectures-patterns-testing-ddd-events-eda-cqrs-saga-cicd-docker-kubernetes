package com.amr.shop.cmmj.common_java_context.shared.enums;

public enum ApplicationValidationEnum {
    EMAIL_ALREADY_EXISTS("Email already exists"),
    COMMAND_EXCEPTION("The <%s> command is not associated"),
    QUERY_EXCEPTION("The <%s> query is not associated");

    private final String message;

    ApplicationValidationEnum(String message) {

        this.message = message;
    }

    public String getMessage() {

        return message;
    }
}
