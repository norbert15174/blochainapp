package com.blockchain.blochainapp.data.functionality.common.service;

import com.blockchain.blochainapp.data.functionality.common.domain.EntityId;

import java.util.Collection;
import java.util.Set;

public interface CudService<E extends EntityId<ID>, ID> {

    E create(E entity);

    Set<E> createAll(Collection<E> entities);

    E update(E entity);

    Set<E> updateAll(Collection<E> entities);

    void delete(E entity);

    void deleteById(ID id);

    void deleteAll(Collection<E> entities);

}
