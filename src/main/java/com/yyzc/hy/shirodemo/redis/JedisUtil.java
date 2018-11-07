package com.yyzc.hy.shirodemo.redis;

import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: songyalong
 * @Description: reids 工具类
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class JedisUtil {
    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(1000);
        jedisPoolConfig.setMaxWaitMillis(100);
        jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    public static void closeJedis(Jedis jedis){
        jedis.close();
    }

}
