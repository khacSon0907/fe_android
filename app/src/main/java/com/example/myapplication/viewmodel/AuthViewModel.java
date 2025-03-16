package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.model.RegisterRequest;
import com.example.myapplication.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private MutableLiveData<String> registerResult = new MutableLiveData<>();

    public LiveData<String> getRegisterResult() {
        return registerResult;
    }

    public void registerUser(RegisterRequest registerRequest) {
        ApiService apiService = ApiClient.getApiService();
        Call<RegisterResponse> call = apiService.registerUser(registerRequest);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    registerResult.setValue("Đăng ký thành công!");
                } else {
                    registerResult.setValue("Đăng ký thất bại!");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                registerResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}