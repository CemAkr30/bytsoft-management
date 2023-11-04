package com.codeforinca.bytsoftapi.bootstrap;


import com.codeforinca.bytsoftapi.persistence.entites.User;
import com.codeforinca.bytsoftapi.persistence.repository.IUserRepository;
import com.codeforinca.bytsoftcore.utils.AesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class DataLoader
    implements CommandLineRunner
{

    private final IUserRepository userRepository;

    public DataLoader(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("Loading data...");
        loadUsers();
    }

    private void loadUsers() throws Exception {
        List<User> list = userRepository.findAll();
        for (User user: list)
        {
            if (user.getUserName().equals("admin"))
                return;
        }
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
