package com.codeforinca.bytsoftapi.models.request;


import com.codeforinca.bytsoftcore.core.abstractions.model.ABaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModulModelRequest
    extends ABaseModel {
    // fields
    private String name;
    private String description;
    private String image;
}
