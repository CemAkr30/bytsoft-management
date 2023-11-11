package com.codeforinca.bytsoftapi.persistence.repository;


import com.codeforinca.bytsoftapi.persistence.entites.Modul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IModulRepository
    extends JpaRepository<Modul,Long> {
}
