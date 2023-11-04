package com.codeforinca.bytsoftapi.controllers.abstraction.controller;

import com.codeforinca.bytsoftcore.core.abstractions.service.ABaseService;
import com.codeforinca.bytsoftcore.core.implemantations.controller.IRsController;
import com.codeforinca.bytsoftcore.core.implemantations.entity.IEntity;
import com.codeforinca.bytsoftcore.core.implemantations.model.IModel;
import org.springframework.web.bind.annotation.*;

public
abstract
class
ABaseRsController<T extends IModel,E extends IEntity,ID extends Long>
    implements IRsController<T,ID>
{
    public abstract ABaseService<T,E,ID> getService();

    @PostMapping("/save")
    @Override
    public T save( @RequestBody T entity) {
        return getService().save(entity);
    }


    @PostMapping("/update")
    @Override
    public T update(@RequestBody T entity) {
        return getService().update(entity);
    }


    @DeleteMapping("/delete")
    @Override
    public void delete(@RequestBody ID id) {
        getService().delete(id);
    }

    @GetMapping("/findById")
    @Override
    public T findById(@RequestBody ID id) {
        return getService().findById(id);
    }


    @GetMapping("/findAll")
    @Override
    public Iterable<T> findAll() {
        return getService().findAll();
    }
}
