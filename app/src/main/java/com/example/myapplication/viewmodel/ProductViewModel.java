package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.model.ProductAdmin;
import com.example.myapplication.respone.ResponseWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends AndroidViewModel {
    private MutableLiveData<List<ProductAdmin>> productListLiveData;
    private MutableLiveData<ProductAdmin> productLiveData;
    private MutableLiveData<String> errorMessageLiveData;
    private MutableLiveData<String> successMessageLiveData;

    public ProductViewModel(Application application) {
        super(application);
        productListLiveData = new MutableLiveData<>();
        productLiveData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();
        successMessageLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<ProductAdmin>> getProductList() {
        return productListLiveData;
    }

    public MutableLiveData<ProductAdmin> getProduct() {
        return productLiveData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public MutableLiveData<String> getSuccessMessage() {
        return successMessageLiveData;
    }

    public void createProduct(ProductAdmin product, File imageFile) {
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), product.getName());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(product.getPrice()));
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), product.getDescription());
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), product.getCategory());
        RequestBody brand = RequestBody.create(MediaType.parse("text/plain"), product.getBrand());

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseWrapper<ProductAdmin>> call = apiService.createProduct(
                name, image, price, description, category, brand
        );

        call.enqueue(new Callback<ResponseWrapper<ProductAdmin>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ProductAdmin>> call, Response<ResponseWrapper<ProductAdmin>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductAdmin productData = response.body().getData();
                    if (productData != null) {
                        productLiveData.setValue(productData);
                        successMessageLiveData.setValue("Tạo sản phẩm thành công!");
                    } else {
                        errorMessageLiveData.setValue("Sản phẩm không có dữ liệu.");
                    }
                } else {
                    errorMessageLiveData.setValue("Lỗi từ server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ProductAdmin>> call, Throwable t) {
                errorMessageLiveData.setValue("Lỗi kết nối mạng: " + t.getMessage());
            }
        });
    }


}