package com.example.myapplication.model;


import java.util.List;

public class Cart {
    private List<Item> items;
    private double totalPrice;

    // Constructor
    public Cart(List<Item> items, double totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    // Getters & Setters
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
