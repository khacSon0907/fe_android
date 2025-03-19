package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.model.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

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
        Call<Map<String, Object>> call = apiService.registerUser(registerRequest);

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful()) {
                    // Nếu đăng ký thành công, cập nhật LiveData với thông điệp thành công
                    Map<String, Object> responseBody = response.body();
                    if (responseBody != null && responseBody.containsKey("message")) {
                        registerResult.setValue(responseBody.get("message").toString());
                    }
                } else {
                    try {
                        // Lấy phản hồi JSON từ lỗi
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        if (jsonObject.has("message")) {
                            // Lấy thông điệp lỗi từ JSON
                            registerResult.setValue(jsonObject.getString("message"));
                        } else {
                            registerResult.setValue("Đăng ký thất bại! Lỗi không xác định.");
                        }
                    } catch (IOException | JSONException e) {
                        registerResult.setValue("Lỗi xử lý phản hồi từ server: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                registerResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}