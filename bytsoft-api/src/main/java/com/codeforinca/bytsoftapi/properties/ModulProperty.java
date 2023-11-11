package com.codeforinca.bytsoftapi.properties;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        addModul(new HashMap<>(),"Identity Management","Identity Management Description","identityManagement.png");
        addModul(new HashMap<>(),"Data Transfer Service","Data Transfer Service Description","dataTransferService.png");
    }


    private
    static
    void
    addModul(
            Map<String,Object> args,
            String ...values
    ){
        args.put("id",value++);
        args.put("name",values[0]);
        args.put("description",values[1]);

        try {
            String currentDirectory = System.getProperty("user.dir"); // Current directory
            File output = new File(currentDirectory  + File.separator +  "picture/images");
            Path imagePath = Paths.get(output.getAbsolutePath() + File.separator + values[2]);
            byte[] imageBytes = Files.readAllBytes(imagePath);
            args.put("image", imageBytes);
        }catch (Exception e){
            args.put("image", null);
        }

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
       for (Map<String,Object> map : modulPropMap){
           if (
                   map.get("id").equals(Integer.parseInt(String.valueOf(id)))
           ){
               return map;
           }
       }
         return new HashMap<>();
    }

}
