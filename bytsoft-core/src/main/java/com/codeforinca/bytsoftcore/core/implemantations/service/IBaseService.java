package com.codeforinca.bytsoftcore.core.implemantations.service;

import com.codeforinca.bytsoftcore.core.implemantations.entity.IEntity;
import com.codeforinca.bytsoftcore.core.implemantations.model.IModel;

public
interface
IBaseService
        <
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
