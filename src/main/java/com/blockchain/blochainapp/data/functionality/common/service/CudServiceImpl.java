package com.blockchain.blochainapp.data.functionality.common.service;

import com.blockchain.blochainapp.data.functionality.common.domain.EntityId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public abstract class CudServiceImpl<R extends JpaRepository<E, ID>, E extends EntityId<ID>, ID> implements CudService<E, ID> {

    private R repository;

    @Override
    public E create(E entity) {
        E created = repository.save(entity);
        log.info("Object {} with id = {} created successfully", created.getEntityName(), created.getId());
        return created;
    }

    @Override
    public Set<E> createAll(Collection<E> entities) {
        entities.forEach(this::create);
        return new HashSet<>(entities);
    }

    @Override
    public E update(E entity) {
        E created = repository.save(entity);
        log.info("Object {} with id = {} updated successfully", created.getEntityName(), created.getId());
        return created;
    }

    @Override
    public Set<E> updateAll(Collection<E> entities) {
        entities.forEach(this::update);
        return new HashSet<>(entities);
    }

    @Override
    public void delete(E entity) {
        repository.delete(entity);
        log.info("Object {} with id = {} deleted successfully", entity.getEntityName(), entity.getId());
    }

    @Override
    public void deleteById(ID id) {
        repository.findById(id).ifPresent(this::delete);
    }

    @Override
    public void deleteAll(Collection<E> entities) {
        entities.forEach(this::delete);
    }
}
