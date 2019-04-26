package com.mr.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mr.order.mapper.OrderMapper;
import com.mr.order.pojo.Order;
import com.mr.order.service.OrderService;
import com.mr.utils.LayResult;
import com.mr.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2019/4/12.
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public LayResult<Order> addOrder(String bankNo, Integer bankState, double orderMoney) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost",6379,15,"rrp");
        Jedis jedis = pool.getResource();

        Order order = null;
        if(bankState == 1 || bankState == 2){
            order = new Order(bankNo,bankState,orderMoney,2,new Date());
        }
        orderMapper.insertAndGetId(order);
        jedis.setex(order.getOrderId().toString(),15,order.getOrderId().toString());
        return new LayResult<Order>(0,"添加成功.",1000,null);
    }

    //查询
    @Override
    public LayResult<Order> orderList(Page page) {
        if(null == page){
            page = new Page();
        }
        PageHelper.startPage(page.getPage(),page.getRows());
        List<Order> orderList =  orderMapper.orderList();
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return new LayResult<Order>(0,"",(int)pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public Order selectById(Integer orderId){
        return orderMapper.selectById(orderId);
    }

    @Override
    public void updateById(String bankNo) {
        orderMapper.updateById(bankNo);
    }

    @Override
    public void updateState(Integer orderId) {
        orderMapper.updateState(orderId);
    }
}
