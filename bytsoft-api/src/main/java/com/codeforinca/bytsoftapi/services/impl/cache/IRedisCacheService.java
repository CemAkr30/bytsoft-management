package com.codeforinca.bytsoftapi.services.impl.cache;

import java.util.Set;

public interface IRedisCacheService
{
    void set(
        String key,
        Object value
    );

    Object get(
        String key
    );

    void delete(
        String key
    );

    void deleteAll();


    void hashPut(
            String key,
            String field,
            String value
    );

    Object hashGet(
            String key,
            String field
    );

    Boolean addToSortedSet(
            String key,
            String value,
            double score
    );


    Set<Object> getSortedSetRangeByScore(
            String key,
            double min,
            double max
    );


    Long addToSet(
            String key,
            String... values
    );


    Set<Object> getSetMembers(
            String key
    );

    Long leftPush(
            String key,
            String value
    );

    String rightPop(
            String key
    );


}
