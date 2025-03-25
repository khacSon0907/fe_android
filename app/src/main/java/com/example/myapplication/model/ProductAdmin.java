package com.example.myapplication.model;

public class ProductAdmin {
    // ProductAdmin.java (Model)
    private String name;
    private String image;
    private double price;
    private String description;
    private String category;
    private String brand;

    // Constructor
    public ProductAdmin(String name, String image, double price, String description, String category, String brand) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.category = category;
        this.brand = brand;
    }

    public ProductAdmin(String name, double price, String description, String category, String brand) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.category = category;
        this.brand = brand;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
}
