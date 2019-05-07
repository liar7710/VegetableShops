package com.Bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;

public class orders {
    public static final String table="orders";
    private int id;
    private double total_prices;
    private int total_quanity;
    private int paystate;
    private Timestamp ordertime;
    private int user_id;
    private String consignee;
    private String tel;
    private String address;

    public static String getTable() {
        return table;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal_prices() {
        return total_prices;
    }

    public void setTotal_prices(double total_prices) {
        this.total_prices = total_prices;
    }

    public int getTotal_quanity() {
        return total_quanity;
    }

    public void setTotal_quanity(int total_quanity) {
        this.total_quanity = total_quanity;
    }

    public int getPaystate() {
        return paystate;
    }

    public void setPaystate(int paystate) {
        this.paystate = paystate;
    }

    public Timestamp getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Timestamp ordertime) {
        this.ordertime = ordertime;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
