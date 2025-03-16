package com.example.myapplication.model;

public class RegisterRequest {
    private String email;
    private String username; // Tương ứng với username trong backend
    private String password;
    private String phonenumber;

    // Constructor
    public RegisterRequest(String email, String username, String password, String phonenumber) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    // Getters và setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}