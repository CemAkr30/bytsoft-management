package com.codeforinca.bytsoftcore.core.abstractions.model;

import com.codeforinca.bytsoftcore.core.implemantations.model.IModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;


@MappedSuperclass
@Getter
public
class
ABaseModel
    implements
        IModel
{
    private Long id;
    private Date created;
    private Date updated;
    @Setter
    private Boolean isDeleted;
}
