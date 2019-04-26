package com.mr.order.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    private Integer orderId;

    private String bankNo;

    private Integer bankState;

    private Double orderMoney;

    private Integer orderState;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    public String getorderDateStr(){
        if(null != orderDate){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(orderDate);
        }
        return "";
    }

    public Order(String bankNo, Integer bankState, Double orderMoney, Integer orderState, Date orderDate) {
        this.bankNo = bankNo;
        this.bankState = bankState;
        this.orderMoney = orderMoney;
        this.orderState = orderState;
        this.orderDate = orderDate;
    }

    public Order(String bankNo, Integer bankState, Double orderMoney, Date orderDate) {
        this.bankNo = bankNo;
        this.bankState = bankState;
        this.orderMoney = orderMoney;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public Order(Integer orderId, String bankNo, Integer bankState, Double orderMoney, Integer orderState, Date orderDate) {
        this.orderId = orderId;
        this.bankNo = bankNo;
        this.bankState = bankState;
        this.orderMoney = orderMoney;
        this.orderState = orderState;
        this.orderDate = orderDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo == null ? null : bankNo.trim();
    }

    public Integer getBankState() {
        return bankState;
    }

    public void setBankState(Integer bankState) {
        this.bankState = bankState;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}