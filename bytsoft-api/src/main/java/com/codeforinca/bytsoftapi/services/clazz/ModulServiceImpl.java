package com.codeforinca.bytsoftapi.services.clazz;


import com.codeforinca.bytsoftapi.persistence.entites.Modul;
import com.codeforinca.bytsoftapi.persistence.repository.IModulRepository;
import com.codeforinca.bytsoftapi.services.impl.IModulService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModulServiceImpl
    implements IModulService {


    private final IModulRepository modulRepository;

    @Override
    public List<Modul> findAll() {
        return modulRepository.findAll();
    }

    @Override
    public Modul findById(Long aLong) {
        return modulRepository.findById(aLong).get();
    }

    @Override
    public Modul persist(Modul entity) {
        return modulRepository.save(entity);
    }

    @Override
    public Modul merge(Modul entity) {
        return modulRepository.save(entity);
    }

    @Override
    public Boolean remove(Long aLong) {
        Boolean isRemove = true;
        try {
            modulRepository.delete(findById(aLong));
        }catch (Exception e){
            isRemove = false;
        }

        return isRemove;
    }
}
