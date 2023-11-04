package com.codeforinca.bytsoftcore.core.implemantations.entity;

import java.util.Date;

public interface IEntity
{
    Long getId();
    Date getCreated();
    Date getUpdated();
    void setIsDeleted(Boolean isDeleted);
    Boolean getIsDeleted();
}
