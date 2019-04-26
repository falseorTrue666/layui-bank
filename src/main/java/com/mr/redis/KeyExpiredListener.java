package com.mr.redis;

import com.mr.order.service.OrderService;
import org.opensaml.xml.signature.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by ruipeng.ren
 * on 2019/4/16.
 * 监听器
 */
@Component
public class KeyExpiredListener extends JedisPubSub {

    @Autowired
    private OrderService orderService;

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe "
                + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

        System.out.println("onPMessage pattern "
                + pattern + " " + channel + " " + message);

        Integer orderId = Integer.parseInt(message);
        orderService.updateState(orderId);
    }



}
