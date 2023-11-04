package com.codeforinca.bytsoftcore.core.implemantations.controller;

import com.codeforinca.bytsoftcore.core.implemantations.model.IModel;

public interface
IRsController<
        T extends IModel,
        ID extends Long
        >
{
    T save(T entity);
    T update(T entity);
    void delete(ID id);
    T findById(ID id);
    Iterable<T> findAll();
}
