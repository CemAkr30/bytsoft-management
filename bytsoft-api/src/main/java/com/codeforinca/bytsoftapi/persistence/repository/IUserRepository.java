package com.codeforinca.bytsoftapi.persistence.repository;


import com.codeforinca.bytsoftapi.persistence.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository
    extends JpaRepository<User,Long>
{
    User findByUserName(String username);
}
