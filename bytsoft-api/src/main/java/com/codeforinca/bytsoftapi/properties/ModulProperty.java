package com.codeforinca.bytsoftapi.properties;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModulProperty {

    private static List<Map<String,Object>> modulPropMap = null;
    private static Integer value;

    static {
        modulPropMap = new LinkedList<>();
        value = 1;
        addModul(new HashMap<>(),"Identity Management");
        addModul(new HashMap<>(),"Data Transfer Service");
    }


    private
    static
    void
    addModul(
            Map<String,Object> args,
            String name
    ){
        args.put("id",value++);
        args.put("name",name);

        modulPropMap.add(args);
    }



    public
    static
    List<Map<String,Object>>
    modulProperties(

    ){
        return modulPropMap;
    }


    public
    static
    Map<String,Object>
    findByModulProperty(
            Long id
    ){
        return modulPropMap
                .stream()
                .filter(
                        mp -> mp.get("id").equals(id)
                ).findFirst()
                .get();
    }

}
