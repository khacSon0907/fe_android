package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.model.LoginRequest;
import com.example.myapplication.model.RegisterRequest;
import com.example.myapplication.model.ResponLogin;
import com.example.myapplication.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private MutableLiveData<String> registerResult = new MutableLiveData<>();
    private MutableLiveData<String> loginResult = new MutableLiveData<>();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getRegisterResult() {
        return registerResult;
    }

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    public void registerUser(RegisterRequest registerRequest) {
        ApiService apiService = ApiClient.getApiService();
        Call<Map<String, Object>> call = apiService.registerUser(registerRequest);

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful()) {
                    Map<String, Object> responseBody = response.body();
                    if (responseBody != null && responseBody.containsKey("message")) {
                        registerResult.setValue(responseBody.get("message").toString());
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        if (jsonObject.has("message")) {
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


    public void loginUser(LoginRequest loginRequest) {
        ApiService apiService = ApiClient.getApiService();
        Call<ResponLogin> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<ResponLogin>() {
            @Override
            public void onResponse(Call<ResponLogin> call, Response<ResponLogin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponLogin loginResponse = response.body();

                    // Lấy thông tin người dùng từ phản hồi
                    User user = loginResponse.getData();
                    String message = loginResponse.getMessage();

                    if (user != null) {
                        // Xử lý khi đăng nhập thành công


                        // Cập nhật UI hoặc lưu thông tin người dùng
                        loginResult.setValue("Đăng nhập thành công! Chào " + user.getUsername());
                    } else {
                        loginResult.setValue("Đăng nhập không thành công: Dữ liệu người dùng bị thiếu.");
                    }
                } else {
                    // Xử lý lỗi khi API trả về phản hồi không thành công
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        if (jsonObject.has("message")) {
                            loginResult.setValue("Đăng nhập thất bại: " + jsonObject.getString("message"));
                        } else {
                            loginResult.setValue("Đăng nhập thất bại! Lỗi không xác định.");
                        }
                    } catch (IOException | JSONException e) {
                        loginResult.setValue("Lỗi xử lý phản hồi từ server: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponLogin> call, Throwable t) {
                loginResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    public void getUserbyEmail(String email) {
        ApiService apiService = ApiClient.getApiService();
        Call<ResponLogin> call = apiService.getUserbyEmail(email);

        call.enqueue(new Callback<ResponLogin>() {
            @Override
            public void onResponse(Call<ResponLogin> call, Response<ResponLogin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponLogin loginResponse = response.body();

                    // Lấy thông tin người dùng từ phản hồi
                    User user = loginResponse.getData();
                    String message = loginResponse.getMessage();

                    if (user != null) {
                        // Cập nhật thông tin người dùng vào LiveData
                        userLiveData.setValue(user);
                        loginResult.setValue("Lấy thông tin người dùng thành công!");
                    } else {
                        loginResult.setValue("Không tìm thấy thông tin người dùng.");
                    }
                } else {
                    // Xử lý lỗi khi API trả về phản hồi không thành công
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        if (jsonObject.has("message")) {
                            loginResult.setValue("Lỗi: " + jsonObject.getString("message"));
                        } else {
                            loginResult.setValue("Lỗi không xác định.");
                        }
                    } catch (IOException | JSONException e) {
                        loginResult.setValue("Lỗi xử lý phản hồi từ server: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponLogin> call, Throwable t) {
                loginResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

}