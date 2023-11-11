package com.codeforinca.bytsoftapi.persistence.entites;


import com.codeforinca.bytsoftcore.core.abstractions.entity.AEntity;
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
    private String image;
    @Column
    private Long modulPropId;
    @ManyToMany(mappedBy = "modules")
    private Set<User> users = new HashSet<>();
}
