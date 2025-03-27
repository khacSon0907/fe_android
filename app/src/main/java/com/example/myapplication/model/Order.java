package com.example.myapplication.model;


import java.util.Date;
import java.util.List;

public class Order {

    private String id;
    private String email;
    private String phonenumber;
    private String address;
    private double totalPrice;
    private String status;
    private Date createdAt;
    private List<OrderItem> items; // Danh sách sản phẩm trong đơn hàng
    public String getId() {
        return id;
    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Order(String id, String email, String phonenumber, String address, String status, double totalPrice, Date createdAt, List<OrderItem> items) {
        this.id = id;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.status = status;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.items = items;
    }
    public  Order(){

    }

}