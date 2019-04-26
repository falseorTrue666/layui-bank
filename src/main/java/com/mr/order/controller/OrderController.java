package com.mr.order.controller;

import com.mr.order.pojo.Order;
import com.mr.order.service.OrderService;
import com.mr.utils.LayResult;
import com.mr.utils.Page;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by DELL on 2019/4/12.
 */
@Controller
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //存还款
    @RequestMapping(value = "openCunOrUpdate")
    @ResponseBody
    public ModelAndView openCunOrUpdate(String bankNo,Integer bankState){
        //orderService
        ModelAndView mv = new ModelAndView("../../order/addOrder");
        mv.addObject("bankNo",bankNo);
        mv.addObject("bankState",bankState);
        return mv;
    }

    //添加订单信息
    @RequestMapping(value = "addOrder")
    @ResponseBody
    public LayResult<Order> addOrder(String bankNo,Integer bankState,double orderMoney){
        return orderService.addOrder(bankNo,bankState,orderMoney);
    }

    //查询
    @RequestMapping(value = "orderList")
    @ResponseBody
    public LayResult<Order> orderList(Page page){
        return orderService.orderList(page);
    }
}
