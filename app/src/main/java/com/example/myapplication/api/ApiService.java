package com.example.myapplication.api;

import com.example.myapplication.model.RegisterRequest;
import com.example.myapplication.model.RegisterResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/register") // Thay "api/register" bằng endpoint thực tế của bạn
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);
}