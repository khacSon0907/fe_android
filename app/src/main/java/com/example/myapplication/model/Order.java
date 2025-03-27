package com.example.myapplication.model;


import java.util.Date;
import java.util.List;

public class Order {

    private String id ;
    private String email;
    private String phonenumber;
    private String address;
    private double totalPrice;
    private String status;
    private Date createdAt;
    private List<OrderItem> items;

}
