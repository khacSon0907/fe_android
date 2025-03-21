package com.example.myapplication.api;

import com.example.myapplication.model.LoginRequest;
import com.example.myapplication.model.RegisterRequest;
import com.example.myapplication.model.ResponLogin;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/register") // Thay "api/register" bằng endpoint thực tế của bạn
    Call<Map<String, Object>> registerUser(@Body RegisterRequest request);

    @POST("api/login")
    Call<ResponLogin> loginUser(@Body LoginRequest request);

    @GET("api/user/email/{email}")
    Call<ResponLogin> getUserbyEmail(@Path("email") String email);
}