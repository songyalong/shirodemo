package com.yyzc.hy.shirodemo.redis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class RedisSessionDao extends CachingSessionDAO {

    private static final String PREFIX = "SHIRO_SESSION_ID";

    private static final int EXPRIE = 10000;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSessionDao.class);

    private byte[] getByteKey(Object k){
        if(k instanceof String){
            String key = PREFIX+k;
            return key.getBytes();
        }else {
            return SerializationUtils.serialize((Serializable) k);
        }
    }

    @Override
    protected void doUpdate(Session session) {
        System.out.println("doUpdate");
        Jedis jedis = JedisUtil.getJedis();
        jedis.setex(getByteKey(session.getId()), EXPRIE, SerializationUtils.serialize(session));
        JedisUtil.closeJedis(jedis);
    }

    @Override
    protected void doDelete(Session session) {
        System.out.println("doDelete");
        if(null == session){
            return ;
        }
        Jedis jedis = JedisUtil.getJedis();
        jedis.del(getByteKey(session.getId()));
        JedisUtil.closeJedis(jedis);
    }

    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("doCreate");
        Serializable serializable = generateSessionId(session);
        assignSessionId(session, serializable);
        Jedis jedis = JedisUtil.getJedis();
        jedis.setex(getByteKey(serializable), EXPRIE, SerializationUtils.serialize(session));
        JedisUtil.closeJedis(jedis);
        return serializable;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        System.out.println("doReadSession");
        Jedis jedis = JedisUtil.getJedis();
        Session session = null;
        byte[] bytes = jedis.get(getByteKey(serializable));
        if(null != bytes){
            session = (Session) SerializationUtils.deserialize(bytes);
            jedis.expire(getByteKey(serializable), EXPRIE);
        }
        if(null == session){
            return null;
        }
        JedisUtil.closeJedis(jedis);
        return session;
    }
}
