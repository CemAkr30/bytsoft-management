package com.codeforinca.bytsoftapi.services.base;

import java.util.List;

public interface ICrudService<T,ID>
{
    List<T> findAll();

    T findById(ID id);

    T persist(T entity);

    T merge(T entity);

    Boolean remove(ID id);
}
