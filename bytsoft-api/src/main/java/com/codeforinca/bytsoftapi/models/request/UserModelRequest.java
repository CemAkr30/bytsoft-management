package com.codeforinca.bytsoftapi.models.request;

import com.codeforinca.bytsoftapi.persistence.entites.Modul;
import com.codeforinca.bytsoftcore.core.abstractions.model.ABaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModelRequest
        extends ABaseModel
{
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean isActive;
    private Map<String,Object> offlineCaptcha;
    private Set<Modul> modules = new HashSet<>();
}
