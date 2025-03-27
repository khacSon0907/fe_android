package com.example.myapplication.model;


public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private String size;

    public OrderItem(String productId, String productName, int quantity, double price, String size) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }

    // Getter/Setter đầy đủ ở đây...
}