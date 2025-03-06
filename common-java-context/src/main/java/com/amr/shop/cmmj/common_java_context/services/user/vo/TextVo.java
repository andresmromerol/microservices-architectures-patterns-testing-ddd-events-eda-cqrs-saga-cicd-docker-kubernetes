package com.amr.shop.cmmj.common_java_context.services.user.vo;

import com.amr.shop.cmmj.common_java_context.shared.exception.DomainException;

public class TextVo {

    private final String value;

    TextVo(String value) {
        this.value = value;
        ensureIsValid();
    }

    public String getValue() {
        return value;
    }

    public void ensureIsValid() {
        String VALID_TEXT_REGEX = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$";
        if (value == null || !value.matches(VALID_TEXT_REGEX)) {
            throw new DomainException("Invalid text");
        }
    }
}
