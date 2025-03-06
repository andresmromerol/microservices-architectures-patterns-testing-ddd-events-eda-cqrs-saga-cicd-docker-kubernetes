package com.amr.shop.cmmj.common_java_context.services.user.vo;

import java.util.Objects;

public class NameVo {
    private final TextVo name;

    public NameVo(String name) {
        this.name = new TextVo(name);
    }

    public String getName() {
        return name.getValue();
    }

    public String getValue() {
        return name.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NameVo nameVo = (NameVo) o;
        return Objects.equals(name, nameVo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
