package com.example.myapplication.model;


public class Product {
    private String name;
    private int price;
    private int imageResource;


    public Product(String name, int price, int imageResource) {
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }

}
