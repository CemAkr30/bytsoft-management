package com.codeforinca.bytsoftcore.core.abstractions.service;

import com.codeforinca.bytsoftcore.core.implemantations.entity.IEntity;
import com.codeforinca.bytsoftcore.core.implemantations.model.IModel;
import com.codeforinca.bytsoftcore.core.implemantations.repository.IBaseRepo;
import com.codeforinca.bytsoftcore.core.implemantations.service.IBaseService;
import net.projectmonkey.object.mapper.ObjectMapper;

import java.util.List;

public
abstract
class
ABaseService<
        T extends IModel,
        E extends IEntity ,
        ID extends Long
        >
    implements
        IBaseService<
        T,ID
        >
{


    public abstract IBaseRepo<E,ID> getBaseRepo();

    protected abstract Class<E> getEntityClass();

    protected abstract Class<T> getModelClass();

    @Override
    public T save(T entity) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.map(getBaseRepo().save(mapper.map(entity,getEntityClass())), getModelClass());
    }

    @Override
    public T update(T entity) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.map(getBaseRepo().update(mapper.map(entity,getEntityClass())), getModelClass());
    }

    @Override
    public void delete(ID id) {
        getBaseRepo().delete(id);
    }

    @Override
    public T findById(ID id) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.map(getBaseRepo().findById(id), getModelClass());
    }

    @Override
    public List<T> findAll() {
        return (List<T>) (List<?>) getBaseRepo().findAll();
    }


    protected ObjectMapper getMapper(){
        return new ObjectMapper();
    }

    protected E mapToEntity(T model){
        return getMapper().map(model,getEntityClass());
    }

    protected T mapToModel(E entity){
        return getMapper().map(entity,getModelClass());
    }

    protected List<E> mapToEntityList(List<T> modelList){
        return getMapper().map(modelList,List.class,getEntityClass());
    }

    protected List<T> mapToModelList(List<E> entityList){
        return getMapper().map(entityList,List.class,getModelClass());
    }

}
