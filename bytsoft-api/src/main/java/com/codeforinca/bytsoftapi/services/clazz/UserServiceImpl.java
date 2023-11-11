package com.codeforinca.bytsoftapi.services.clazz;


import com.codeforinca.bytsoftapi.auth.JwtTokenBuilder;
import com.codeforinca.bytsoftapi.exceptions.UserException;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.persistence.entites.Modul;
import com.codeforinca.bytsoftapi.persistence.entites.User;
import com.codeforinca.bytsoftapi.persistence.repository.IUserRepository;
import com.codeforinca.bytsoftapi.properties.ModulProperty;
import com.codeforinca.bytsoftapi.services.impl.IUserService;
import com.codeforinca.bytsoftapi.singletonCache.OfflineCaptchaCache;
import com.codeforinca.bytsoftcore.utils.AesUtils;
import com.codeforinca.bytsoftcore.utils.ImageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl
            implements IUserService
{

    private final IUserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public ApiResponse findByEmailAndPassword(String email, String password) throws Exception {
        List<User> users = userRepository.findAll();
        User user = null;
        for (User uFor : users)
        {
            if (uFor.getEmail().equals(email))
            {
                user = uFor;
                break;
            }
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);

        if ( user == null )
        {
            apiResponse.setMessage("User not found");
            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            return apiResponse;
        }

        if (!user.getPassword().equals(AesUtils.encrypt(password)))
        {
            apiResponse.setMessage("Password not match");
            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            return apiResponse;
        }

        apiResponse.setData(user);
        apiResponse.setToken("Bearer " + JwtTokenBuilder.generateToken(user.getUserName()));
        apiResponse.setMessage("Login success");

       return apiResponse;
    }

    @Override
    public Map<String, Object> offlineCaptcha() {
        String uuID = UUID.randomUUID().toString();
        String isMessage = "";
        int width = 150;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE); // Background color
        g2d.fillRect(0, 0, width, height);

        String captchaText = generateRandomText(6); // 6 character captcha text
        g2d.setColor(Color.BLACK);
        //g2d.setStroke(new BasicStroke(2));
        //g2d.drawRect(0, 0, width - 1, height - 1);

        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        int x = 20;
        for (int i = 0; i < captchaText.length(); i++)
        {
            int y = generateRandomValue(20, height - 10);
            g2d.drawString(String.valueOf(captchaText.charAt(i)), x, y);
            x += 20;
        }

        String currentDirectory = System.getProperty("user.dir"); // Current directory
        File output = new File(currentDirectory  + File.separator +  "picture");
        if (!output.exists() || !output.isDirectory())
        {
            output.mkdir();
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.command("chmod","755",output.getAbsolutePath() );
            processBuilder.command("chmod","777",output.getAbsolutePath());
            try
            {
                Process process = processBuilder.start();
            }
            catch (IOException e)
            {
                isMessage = "Error while creating captcha";
                return null;
            }
        }

        try
        {
            String childPath = File.separator + uuID + ".png";
            File file = new File(output.getAbsolutePath() + childPath);
            ImageIO.write(image, "png", file);
            Map<String,Object> obj = new HashMap<>();
            obj.put("captchaPath",file.getAbsolutePath());
            obj.put("createDate",new Date());
            obj.put("captchaText",captchaText);
            OfflineCaptchaCache.getInstance().put(uuID,obj);
            byte[] base64ImageBytes = null;
            try (InputStream inputStream = new FileInputStream(file))
            {
                byte[] imageBytes = new byte[(int) file.length()];
                inputStream.read(imageBytes);
                base64ImageBytes = Base64.getEncoder().encode(imageBytes);
            }
            Map<String,Object> responseMap = new HashMap<>();
            responseMap.put("uuID",uuID);
            responseMap.put("captchaBase64","data:image/png;base64," + new String(base64ImageBytes));

            return responseMap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
           e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object checkOfflineCaptcha(Map<String, Object> args) {
        String uuID = String.valueOf(args.get("uuID"));
        String textCode = String.valueOf(args.get("textCode"));

        Map<String, Object> objectMap = (Map<String, Object>) OfflineCaptchaCache.getInstance().get(uuID);
        if (objectMap != null) {
            String captchaText = String.valueOf(objectMap.get("captchaText"));
            if (captchaText != null && textCode.equals(captchaText)) {
                OfflineCaptchaCache.getInstance().remove(uuID);
                String currentDirectory = System.getProperty("user.dir"); // Geçerli dizin
                File output = new File(currentDirectory + File.separator + "picture" + File.separator + uuID + ".png");
                if (output.exists() && output.delete()) {
                    return "{\"status\":\"true\"}";
                }
            }
        }

        return "{\"status\":\"false\"}";
    }


    @Override
    public Object authorizationModuls(String userName) {
        User user = userRepository.findByUserName(userName);
        if (
                user==null
        ){
            throw new UserException(
                    "User not found in database"
            );
        }

        List<Object> moduls = user.getModules().stream()
                .map(modul -> {
                    Map<String, Object> modulMap = new HashMap<>();
                    modulMap.put("id", modul.getId());
                    modulMap.put("name", modul.getName());
                    Map<String,Object> map = ModulProperty.findByModulProperty(modul.getModulPropId());
                    if (map.get("image")!=null)
                    {
                        modulMap.put("image", "data:image/png;base64," + ImageBuilder.encodeImage((byte[]) map.get("image")));
                    }else{
                        modulMap.put("image", null);
                    }
                    modulMap.put("modulPropId", modul.getModulPropId());
                    modulMap.put("description", modul.getDescription());
                    return modulMap;
                })
                .collect(Collectors.toList());


        return  moduls;
    }


    private static int generateRandomValue(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    private static String generateRandomText(int length)
    {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder();

        for (int i = 0; i < length; i++)
        {
            int index = random.nextInt(characters.length());
            captchaText.append(characters.charAt(index));
        }

        return captchaText.toString();
    }


}
