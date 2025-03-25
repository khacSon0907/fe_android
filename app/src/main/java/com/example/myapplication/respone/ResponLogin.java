package com.example.myapplication.respone;

import com.example.myapplication.model.User;

public class ResponLogin {

    private User data;
    private String message;

    public User getData() { return data; }
    public String getMessage() { return message; }

    public void setData(User data) { this.data = data; }
    public void setMessage(String message) { this.message = message; }
}
