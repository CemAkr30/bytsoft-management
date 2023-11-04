package com.codeforinca.bytsoftcore.core.implemantations.repository;

import com.codeforinca.bytsoftcore.core.implemantations.entity.IEntity;

public interface
IBaseRepo<
        T extends IEntity,
        ID extends Long
        >
{
    T save(T entity);
    T update(T entity);
    void delete(ID id);
    T findById(ID id);
    Iterable<T> findAll();
}
