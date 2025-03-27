package com.example.myapplication.view.cart;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private ListView listViewCart;
    private TextView textViewTotal;
    private Button btnOrder, buttonContinueShopping;

    private CartAdapter cartAdapter;
    private AuthViewModel authViewModel;
    private String email;
    private String currentPhoneNumber = "";

    private ArrayList<Item> selectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listviewcart);
        textViewTotal = findViewById(R.id.textview_giatri);
        buttonContinueShopping = findViewById(R.id.buttontieptucmuahang);
        btnOrder = findViewById(R.id.btnOrder);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        email = prefs.getString("email", null);

        if (email == null) {
            Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        authViewModel.getUserbyEmail(email);

        authViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                currentPhoneNumber = user.getPhonenumber(); // ⬅ Lưu lại sdt
            }
        });
        // Lắng nghe dữ liệu giỏ hàng từ ViewModel
        authViewModel.getCart(email);
        authViewModel.getCartLiveData().observe(this, cart -> {
            if (cart != null && !cart.getItems().isEmpty()) {
                if (cartAdapter == null) {
                    cartAdapter = new CartAdapter(this, cart, authViewModel, email);
                    listViewCart.setAdapter(cartAdapter);
                } else {
                    // ✅ Cập nhật dữ liệu mới
                    cartAdapter.updateCart(cart); // thêm hàm này
                }
                recalculateTotal();
            } else {
                textViewTotal.setText("0 Đ");
                listViewCart.setAdapter(null); // clear UI nếu giỏ hàng rỗng
                Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
            }
        });

        // Lắng nghe thông báo thành công
        authViewModel.getSuccessLiveData().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                authViewModel.getCart(email); // Reload cart sau khi thao tác
            }
        });

        // Lắng nghe thông báo thất bại
        authViewModel.getErrorLiveData().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });


        buttonContinueShopping.setOnClickListener(v -> finish());

        btnOrder.setOnClickListener(v -> {
            Cart cart = authViewModel.getCartLiveData().getValue();

            if (cart == null || cart.getItems() == null) return;

            selectedItems.clear(); // Xóa dữ liệu cũ

            for (Item item : cart.getItems()) {
                if (item.isSelected()) {
                    selectedItems.add(item);
                }
            }

            if (selectedItems.isEmpty()) {
                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Vui lòng chọn ít nhất một sản phẩm để đặt hàng.")
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("selectedItems", new ArrayList<>(selectedItems));
                intent.putExtra("email", email);
                intent.putExtra("phonenumber", currentPhoneNumber); // lấy từ user
                startActivity(intent);
            }
        });

    }

    // ✅ Cập nhật lại tổng tiền khi checkbox thay đổi
    public void recalculateTotal() {
        Cart cart = authViewModel.getCartLiveData().getValue();
        if (cart == null || cart.getItems() == null) return;

        double total = 0;
        for (Item item : cart.getItems()) {
            if (item.isSelected()) {
                total += item.getPrice() * item.getQuantity();
            }
        }

        textViewTotal.setText(String.format(Locale.getDefault(), "%,.0f Đ", total));
    }


}
