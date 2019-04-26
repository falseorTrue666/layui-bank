package com.mr.order.mapper;

import com.mr.order.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertAndGetId(Order order);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //查询
    List<Order> orderList();

    //查询一条数据
    Order selectById(Integer orderId);

    //修改支付状态
    void updateById(String bankNo);

    //更改付款状态
    void updateState(Integer orderId);
}