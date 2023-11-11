package com.codeforinca.bytsoftapi.persistence.entites;


import com.codeforinca.bytsoftcore.core.abstractions.entity.AEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User
    extends AEntity
{

    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "is_active")
    private Boolean isActive;
    @ManyToMany(cascade = {CascadeType.ALL} , fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_modules",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "module_id")}
    )
    private Set<Modul> modules = new HashSet<>();
}
