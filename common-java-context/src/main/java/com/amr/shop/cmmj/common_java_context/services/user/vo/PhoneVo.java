package com.amr.shop.cmmj.common_java_context.services.user.vo;

import com.amr.shop.cmmj.common_java_context.shared.exception.DomainException;

public class PhoneVo {
    private final String value;

    public PhoneVo(String value) {
        this.value = value;
        ensureRankIsValid();
    }

    public String getValue() {
        return value;
    }

    private void ensureRankIsValid() {
        if (value == null || value.length() < 8 || value.length() > 40) {
            throw new DomainException("Phone number must be between 5 and 40 characters");
        }
    }
}
