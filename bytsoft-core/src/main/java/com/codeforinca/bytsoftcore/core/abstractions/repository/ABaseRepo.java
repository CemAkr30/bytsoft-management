package com.codeforinca.bytsoftcore.core.abstractions.repository;


import com.codeforinca.bytsoftcore.core.implemantations.entity.IEntity;
import com.codeforinca.bytsoftcore.core.implemantations.repository.IBaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

public
abstract
class
ABaseRepo<T extends IEntity,ID extends Long>
    implements IBaseRepo<T,ID>
{

    @Autowired
    private EntityManager entityManager;

    private Class<T> entityClass;

    public ABaseRepo(
           Class<T> entity
    ){
        entityClass = entity;
    }
    protected Class<T> getEntityClass(){
        return entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(
            EntityManager entityManager
    ){
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public T save(
            T entity
    ){
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public T update(
            T entity
    ){
        return entityManager.merge(entity);
    }

    @Transactional
    @Override
    public void delete(
            ID id
    ){
          T entity = findById(id);
          entityManager.remove(entity);
    }

    @Transactional
    @Override
    public T findById(
            ID id
    ){
        return entityManager.createQuery("from "+getEntityClass().getName()+" where id = :id",getEntityClass())
                .setParameter("id",id)
                .getSingleResult();
    }

    @Transactional
    @Override
    public List<T> findAll(
    ){
        return entityManager.createQuery("from "+getEntityClass().getName()).getResultList();
    }


}
