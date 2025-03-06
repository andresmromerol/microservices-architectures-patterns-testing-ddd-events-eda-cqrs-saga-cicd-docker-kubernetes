package com.amr.shop.cmmj.common_java_context.services.user.vo;

import com.amr.shop.cmmj.common_java_context.shared.exception.DomainException;
import java.util.Objects;

public class PasswordVo {

    private final String value;

    public PasswordVo(String value) {

        if (value == null || value.length() <= 8) {

            throw new DomainException("Password must be valid");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordVo that = (PasswordVo) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(value);
    }
}
