package com.example.myapplication.view.receipt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Order;
import com.example.myapplication.view.customAdapter.ReceiptAdapter;
import com.example.myapplication.view.home.MainActivity;
import com.example.myapplication.viewmodel.OrderviewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private String email;
    private OrderviewModel orderviewModel;
    private Button btn_back_receipt;
    private ReceiptAdapter adapter;
    private List<Order> orderList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // Khởi tạo ViewModel
        orderviewModel = new ViewModelProvider(this).get(OrderviewModel.class);

        btn_back_receipt = findViewById(R.id.btn_back_receipt);
        btn_back_receipt.setOnClickListener(v -> {
            Intent intent = new Intent(ReceiptActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReceiptAdapter(orderList, this, new ReceiptAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                // Sẽ làm sau: mở ReceiptDetailActivity
                Intent intent = new Intent(ReceiptActivity.this, ReceiptDetailActivity.class);
                intent.putExtra("order_json", new Gson().toJson(order));
                startActivity(intent);
            }

            @Override
            public void onCancelOrderClick(String orderId) {
                // Giả lập xoá đơn (có thể gọi API ở đây)

                Toast.makeText(ReceiptActivity.this, "Đã hủy đơn " + orderId, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);


        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        email = prefs.getString("email", null);
        // Gọi API lấy danh sách hóa đơn theo email

        orderviewModel.getReceiptsByEmail(email);

        // Quan sát LiveData để hiển thị danh sách hóa đơn
        orderviewModel.getReceiptListLiveData().observe(this, orders -> {
            adapter.updateData(orders);
        });

        // Quan sát lỗi
        orderviewModel.getErrorLiveData().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }
}
