package com.example.myapplication.view.home;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Cart;
import com.example.myapplication.view.customAdapter.CartAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ListView listViewCart;
    private TextView textViewTotal;
    private Button buttonCheckout, buttonContinueShopping;
    private Cart cart;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listviewcart);
        textViewTotal = findViewById(R.id.textview_giatri);
        buttonCheckout = findViewById(R.id.buttonthanhtoangiohang);
        buttonContinueShopping = findViewById(R.id.buttontieptucmuahang);

        cart = new Cart(new ArrayList<>(), 0);

        cartAdapter = new CartAdapter(this, cart);
        listViewCart.setAdapter(cartAdapter);

        updateTotalPrice();

        buttonCheckout.setOnClickListener(v -> {
            // Xử lý thanh toán
        });

        buttonContinueShopping.setOnClickListener(v -> {
            // Xử lý tiếp tục mua hàng
            finish();
        });
    }

    private void updateTotalPrice() {
        double total = 0;
        for (int i = 0; i < cart.getItems().size(); i++) {
            total += cart.getItems().get(i).getPrice() * cart.getItems().get(i).getQuantity();
        }
        textViewTotal.setText(String.format("%,.0f Đ", total));
    }
}
