package com.example.myapplication.view.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Item;
import com.example.myapplication.view.customAdapter.CartAdapter;
import com.example.myapplication.viewmodel.AuthViewModel;

import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private ListView listViewCart;
    private TextView textViewTotal;
    private Button buttonCheckout, buttonContinueShopping;

    private CartAdapter cartAdapter;
    private AuthViewModel authViewModel;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listviewcart);
        textViewTotal = findViewById(R.id.textview_giatri);
        buttonCheckout = findViewById(R.id.buttonthanhtoangiohang);
        buttonContinueShopping = findViewById(R.id.buttontieptucmuahang);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        email = prefs.getString("email", null);

        if (email == null) {
            Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Lắng nghe dữ liệu giỏ hàng từ ViewModel
        authViewModel.getCart(email);
        authViewModel.getCartLiveData().observe(this, cart -> {
            if (cart != null && !cart.getItems().isEmpty()) {
                cartAdapter = new CartAdapter(this, cart, authViewModel, email);
                listViewCart.setAdapter(cartAdapter);
                updateTotalPrice(cart);
            } else {
                Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
            }
        });

        // Lắng nghe thông báo thành công
        authViewModel.getSuccessLiveData().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                authViewModel.getCart(email); // Reload cart sau khi xoá
            }
        });

        // Lắng nghe thông báo thất bại
        authViewModel.getErrorLiveData().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        buttonCheckout.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng thanh toán chưa hỗ trợ", Toast.LENGTH_SHORT).show();
        });

        buttonContinueShopping.setOnClickListener(v -> finish());
    }

    private void updateTotalPrice(Cart cart) {
        double total = 0;
        for (Item item : cart.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        textViewTotal.setText(String.format(Locale.getDefault(), "%,.0f Đ", total));
    }
}
