package com.yyzc.hy.shirodemo.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class JedisCache<K, V> implements Cache<K, V>, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisCache.class);

    private static final String PREFIX = "SHIRO_SESSION_ID";

    private byte[] getKeyByte(K k){
        if(k instanceof  String){
            return (PREFIX + k).getBytes();
        }else{
            return SerializationUtils.serialize(k);
        }
    }

    @Override
    public V get(K k) throws CacheException {

        if(null == k){
            return null;
        }
        Jedis jedis = JedisUtil.getJedis();
        byte[] bytes = jedis.get(getKeyByte(k));
        if(null == bytes){
            return null;
        }
        JedisUtil.closeJedis(jedis);
        return (V) SerializationUtils.deserialize(bytes);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        Jedis jedis = JedisUtil.getJedis();
        jedis.set(getKeyByte(k), SerializationUtils.serialize(v));
        jedis.expire(getKeyByte(k), 600);
        JedisUtil.closeJedis(jedis);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
