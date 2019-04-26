package com.mr.order.service;

import com.mr.order.pojo.Order;
import com.mr.utils.LayResult;
import com.mr.utils.Page;

/**
 * Created by DELL on 2019/4/12.
 */
public interface OrderService {
    //添加
    LayResult<Order> addOrder(String bankNo, Integer bankState, double orderMoney);

    //查询
    LayResult<Order> orderList(Page page);

    Order selectById(Integer orderId);

    //修改支付状态
    void updateById(String bankNo);

    //更改付款状态
    void updateState(Integer orderId);
}
