package com.codeforinca.bytsoftapi.bootstrap;


import com.codeforinca.bytsoftapi.persistence.entites.Modul;
import com.codeforinca.bytsoftapi.persistence.entites.User;
import com.codeforinca.bytsoftapi.persistence.repository.IModulRepository;
import com.codeforinca.bytsoftapi.persistence.repository.IUserRepository;
import com.codeforinca.bytsoftapi.properties.ModulProperty;
import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import com.codeforinca.bytsoftcore.utils.AesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class DataLoader
    implements CommandLineRunner
{

    private final IUserRepository userRepository;
    private final IModulRepository modulRepository;

    private final IRedisCacheService redisCacheService;

    public DataLoader(IUserRepository userRepository, IModulRepository modulRepository, IRedisCacheService redisCacheService) {
        this.userRepository = userRepository;
        this.modulRepository = modulRepository;
        this.redisCacheService = redisCacheService;
    }
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("Loading data...");
        loadUsers();
        loadModuls();
        clearRedisCache();
    }

    private
    void
    loadUsers(

    ) throws Exception {
        AtomicBoolean isExists = new AtomicBoolean(true);
        List<User> list = userRepository.findAll();
        list.forEach(
                user -> {
                    if(
                            user.getUserName().equals("admin")
                    ){
                        isExists.set(false);
                    }
                }
        );
        if (
                isExists.get()
        ) {
            User user = new User();
            user.setUserName("admin");
            user.setPassword(AesUtils.encrypt("admin"));
            user.setEmail("admin@forinca.com");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPhone("123456789");
            user.setIsActive(true);

            userRepository.save(user);
        }
    }


    private
    void
    loadModuls(

    ) throws Exception {
         List<Modul> moduls = modulRepository.findAll();
         List<Map<String,Object>> modulProperties = ModulProperty.modulProperties();
         List<Long> modulPropIds = new LinkedList<>();
         moduls.forEach(
                 mFor -> modulPropIds.add(mFor.getModulPropId())
         );

         modulProperties
                 .forEach(
                         mpFor -> {
                             Long id = Long.parseLong(mpFor.get("id").toString());
                             String name = mpFor.get("name").toString();
                             String description = mpFor.get("description").toString();

                             if (
                                     !modulPropIds.contains(id)
                             ){
                                 Modul modul = new Modul();
                                 modul.setModulPropId(id);
                                 modul.setDescription(description);
                                 modul.setName(name);

                                 modulRepository.save(modul);
                             }
                         }
                 );

        User user = userRepository.findByUserName("admin");
        if (user != null) {
            Set<Modul> allModuls = new HashSet<>(modulRepository.findAll());
            Set<Modul> userModuls = user.getModules();
            allModuls.removeAll(userModuls);
            userModuls.addAll(allModuls);
            userRepository.save(user);
        }
    }



    private
    void
    clearRedisCache(

    ){
        redisCacheService.delete("#tokens");
    }


}
