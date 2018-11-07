package com.yyzc.hy.shirodemo.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class JedisCacheManager implements CacheManager {

    private final static ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        Cache cache = caches.get(s);
        if(null == cache){
            cache = new JedisCache();
            caches.put(s, cache);
        }
        return cache;
    }
}
