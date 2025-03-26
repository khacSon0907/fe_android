package com.example.myapplication.api;

import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Item;
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
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @Multipart
    @PUT("api/products/update/{id}")
    Call<ResponseWrapper<ProductAdmin>> updateProduct(
            @Path("id") String id,
            @Part("name") RequestBody name,
            @Part MultipartBody.Part image,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("category") RequestBody category,
            @Part("brand") RequestBody brand
    );
    @POST("api/cart/add")
    Call<Void> addToCart(@Query("email") String email, @Body Item item);

    @GET("api/cart")
    Call<Cart> getCart(@Query("email") String email);

    @DELETE("api/cart/remove")
    Call<ResponseWrapper<Void>> deleteCartItem(
            @Query("email") String email,
            @Query("productId") String productId,
            @Query("size") String size
    );


}