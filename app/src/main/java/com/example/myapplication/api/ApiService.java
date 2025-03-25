package com.example.myapplication.api;

import com.example.myapplication.model.LoginRequest;
import com.example.myapplication.model.ProductAdmin;
import com.example.myapplication.model.RegisterRequest;
import com.example.myapplication.respone.ResponLogin;
import com.example.myapplication.respone.ResponseWrapper;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/register") // Thay "api/register" bằng endpoint thực tế của bạn
    Call<Map<String, Object>> registerUser(@Body RegisterRequest request);

    @POST("api/login")
    Call<ResponLogin> loginUser(@Body LoginRequest request);

    @GET("api/user/email/{email}")
    Call<ResponLogin> getUserbyEmail(@Path("email") String email);

    @Multipart
    @POST("api/products/create")
    Call<ResponseWrapper<ProductAdmin>> createProduct(
            @Part("name") RequestBody name,
            @Part MultipartBody.Part image,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("category") RequestBody category,
            @Part("brand") RequestBody brand
    );
    @GET("/api/products")
    Call<List<ProductAdmin>> getProducts();

    @DELETE("api/products/delete/{id}")
    Call<Map<String, String>> deleteProduct(@Path("id") String id);
}