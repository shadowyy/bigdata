package com.alix.bean;

/**
 * @author yuyue
 * @version 2017-11-3 0003 10:06
 */
public class Order {
    //订单商品名称
    private String name;
    //订单价格
    private Float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
