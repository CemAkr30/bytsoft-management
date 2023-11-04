package com.codeforinca.bytsoftcore.core.abstractions.entity;

import com.codeforinca.bytsoftcore.core.implemantations.entity.IEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
@Getter
public
class
AEntity
    implements IEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;

    @Setter
    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
