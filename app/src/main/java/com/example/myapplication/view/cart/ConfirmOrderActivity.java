package com.example.myapplication.view.cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.view.customAdapter.SelectedItemAdapter;
import com.example.myapplication.viewmodel.AuthViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConfirmOrderActivity extends AppCompatActivity {

    private ListView listViewSelected;
    private EditText editPhone, editAddress;
    private TextView textTotal;
    private Button btnConfirm,btnReturn;

    private List<Item> selectedItems;
    private String email;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        listViewSelected = findViewById(R.id.listViewSelectedItems);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        textTotal = findViewById(R.id.textTotalPrice);
        btnConfirm = findViewById(R.id.btnConfirmOrder);
        btnReturn = findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(v -> finish());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Nhận dữ liệu từ Intent
        selectedItems = (ArrayList<Item>) getIntent().getSerializableExtra("selectedItems");
        email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phonenumber");

        if (phone != null) editPhone.setText(phone);

        // Hiển thị sản phẩm
        SelectedItemAdapter adapter = new SelectedItemAdapter(this, selectedItems);
        listViewSelected.setAdapter(adapter);

        // Tính tổng tiền
        double total = 0;
        for (Item item : selectedItems) {
            total += item.getPrice() * item.getQuantity();
        }

        textTotal.setText(String.format(Locale.getDefault(), "Tổng tiền: %,.0f Đ", total));

        btnConfirm.setOnClickListener(v -> {
            String address = editAddress.getText().toString().trim();
            String phoneNumber = editPhone.getText().toString().trim();

            if (address.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }


            Toast.makeText(this, "Xác nhận đơn hàng thành công!", Toast.LENGTH_SHORT).show();
            finish(); // hoặc chuyển sang trang thành công
        });
    }
}

