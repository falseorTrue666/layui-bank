package com.mr.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import   javax.servlet.*;

import javax.servlet.ServletContextListener;

/**
 * Created by DELL on 2019/4/16.
 * 订阅者
 */
@Component
public class Subscriber implements ServletContextListener {

    @Autowired
    private KeyExpiredListener keyExpiredListener;

    //当Tomcat启动时会执行contextInitialized（）
    public   void   contextInitialized(ServletContextEvent   e) {
        new   MyThread(keyExpiredListener).start();
    }
    public   void   contextDestroyed(ServletContextEvent   e) {

    }
    class   MyThread   extends   Thread
    {
        public MyThread(KeyExpiredListener keyExpiredListener1) {
            keyExpiredListener1 = keyExpiredListener;
        }

        public   void   run()
        {
            JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost",6379,15,"rrp");

            Jedis jedis = pool.getResource();
            jedis.psubscribe(keyExpiredListener, "__key*__:*");
        }
    }

}