package com.example.myapplication.model;


public class RegisterResponse {
    private String message;
    private boolean success;

    // Constructor, getters, và setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}