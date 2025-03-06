package com.amr.shop.cmmj.common_java_context.shared.abstracts;

import java.util.Objects;

public abstract class Entity<ID> {

    private ID id;

    public ID getId() {

        return id;
    }

    public void setId(ID id) {

        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(id);
    }
}
