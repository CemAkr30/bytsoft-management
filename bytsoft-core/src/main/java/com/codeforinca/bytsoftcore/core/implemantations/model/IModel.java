package com.codeforinca.bytsoftcore.core.implemantations.model;

import java.util.Date;

public
interface
IModel
{
    Long getId();
    Date getCreated();
    Date getUpdated();
    void setIsDeleted(Boolean isDeleted);
    Boolean getIsDeleted();
}
