package com.codeforinca.bytsoftapi.singletonCache;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class OfflineCaptchaCache {
    private Map<String,Object> args = null;

    private OfflineCaptchaCache()
    {
        args = new java.util.HashMap<>();
    }

    private static OfflineCaptchaCache INSTANCE = null;

    public static OfflineCaptchaCache getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE =  new OfflineCaptchaCache();
        }
        else
        {
            INSTANCE.clear();
        }
        return INSTANCE;
    }

    public void put(String key, Object value)
    {
        args.put(key, value);
    }

    public Object get(String key)
    {
        return args.get(key);
    }

    public void remove(String key)
    {
        args.remove(key);
    }


    private void clear()
    {
        Map<String, Object> obj = args;
        Date now = new Date();

        Iterator<Map.Entry<String, Object>> iterator = obj.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Map<String, Object> value = (Map<String, Object>) entry.getValue();
            Date beforeDate = (Date) value.get("createDate");
            long diff = now.getTime() - beforeDate.getTime();
            long diffMinutes = diff / (60 * 1000) % 60;

            if (diffMinutes > 5) {
                iterator.remove(); // Koleksiyonu güvenli bir şekilde kaldırmak için iterator kullanılır.
            }
        }

    }
}
