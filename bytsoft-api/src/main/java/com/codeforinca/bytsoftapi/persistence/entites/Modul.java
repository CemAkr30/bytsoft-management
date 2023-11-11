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
@Table(name = "modul")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Modul
        extends AEntity {
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Long modulPropId;
    @JsonIgnore // to avoid infinite recursion
    @ManyToMany(mappedBy = "modules" , fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();
}
