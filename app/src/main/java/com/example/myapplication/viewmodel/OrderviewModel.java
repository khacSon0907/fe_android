package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.model.Order;
import com.example.myapplication.respone.ResponseWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderviewModel extends AndroidViewModel {

    private final MutableLiveData<Order> createdOrderLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> messageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public OrderviewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Order> getCreatedOrderLiveData() {
        return createdOrderLiveData;
    }

    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // Hàm gọi API tạo đơn hàng
    public void createOrder(Order order) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseWrapper<Order>> call = apiService.createOrder(order);

        call.enqueue(new Callback<ResponseWrapper<Order>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<Order>> call, Response<ResponseWrapper<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    createdOrderLiveData.setValue(response.body().getData());
                    messageLiveData.setValue(response.body().getMessage());
                } else {
                    errorLiveData.setValue("Tạo đơn hàng thất bại (lỗi response)!");
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<Order>> call, Throwable t) {
                errorLiveData.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
