package com.blockchain.blochainapp.data.functionality.common.domain;

import java.util.Objects;

public interface EntityId<ID> {

    ID getId();

    default boolean hasId(ID id) {
        return Objects.equals(getId(), id);
    }

    default String getEntityName() {
        return getClass().getSimpleName();
    }

}
