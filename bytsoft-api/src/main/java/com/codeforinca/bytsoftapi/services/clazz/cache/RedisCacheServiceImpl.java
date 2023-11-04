package com.codeforinca.bytsoftapi.services.clazz.cache;

import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class RedisCacheServiceImpl
    implements IRedisCacheService
{
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(
            String key,
            Object value
    ){
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(
            String key
    ){
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(
            String key
    ){
        redisTemplate.delete(key);
    }

    @Override
    public void deleteAll(){
        redisTemplate.delete(redisTemplate.keys("*"));
    }


    @Override
    public void hashPut(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }
    @Override
    public Object hashGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }



    @Override
    public Boolean addToSortedSet(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }


    @Override
    public Set<Object> getSortedSetRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }


    @Override
    public Long addToSet(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }
    @Override
    public Set<Object> getSetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }


    @Override
    public Long leftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }


    @Override
    public String rightPop(String key) {
        return (String) redisTemplate.opsForList().rightPop(key);
    }


}
