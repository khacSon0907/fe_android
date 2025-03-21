package com.example.myapplication.api;

import com.example.myapplication.model.LoginRequest;
import com.example.myapplication.model.RegisterRequest;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/register") // Thay "api/register" bằng endpoint thực tế của bạn
    Call<Map<String, Object>> registerUser(@Body RegisterRequest request);

    @POST("api/login")
    Call<Map<String, Object>> loginUser(@Body LoginRequest request);
}