package com.teamflow.forestory_be.support.common.domain;

import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.util.Objects;

public abstract class DomainEntity {

    public abstract Long id();

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        DomainEntity entity = (DomainEntity) other;
        return Objects.equals(id(), entity.id());
    }

    @Override
    public int hashCode() {
        return id().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id=" + id() + ")";
    }

    public static Long generateId() {
        return TsidGenerator.generate();
    }
}
