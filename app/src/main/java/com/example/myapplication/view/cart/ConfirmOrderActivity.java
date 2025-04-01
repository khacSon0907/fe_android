package com.example.myapplication.view.cart;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.view.customAdapter.SelectedItemAdapter;
import com.example.myapplication.view.home.MainActivity;
import com.example.myapplication.view.receipt.ReceiptActivity;
import com.example.myapplication.viewmodel.AuthViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.model.OrderItem;
import com.example.myapplication.model.Order;
import com.example.myapplication.viewmodel.OrderviewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConfirmOrderActivity extends AppCompatActivity {

    private ListView listViewSelected;
    private EditText editPhone, editAddress;
    private TextView textTotal;
    private Button btnConfirm, btnReturn;

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
        OrderviewModel orderviewModel = new ViewModelProvider(this).get(OrderviewModel.class);

        // Nhận dữ liệu từ Intent
        selectedItems = (ArrayList<Item>) getIntent().getSerializableExtra("selectedItems");
        email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phonenumber");

        if (phone != null) editPhone.setText(phone);

        SelectedItemAdapter adapter = new SelectedItemAdapter(this, selectedItems);
        listViewSelected.setAdapter(adapter);

        double total = 0;
        for (Item item : selectedItems) {
            total += item.getPrice() * item.getQuantity();
        }
        textTotal.setText(String.format(Locale.getDefault(), "Tổng tiền: %,.0f Đ", total));

        orderviewModel.getMessageLiveData().observe(this, message -> {
            // ✅ Duyệt từng item và xóa cách nhau 300ms để tránh ghi đè LiveData
            new Thread(() -> {
                for (int i = 0; i < selectedItems.size(); i++) {
                    Item item = selectedItems.get(i);
                    runOnUiThread(() -> {
                        authViewModel.deleteCartItem(email, item.getProductId(), item.getSize());
                    });
                    try {
                        Thread.sleep(300); // ✅ Delay giữa mỗi lần xóa để tránh conflict backend
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // ✅ Gọi lại getCart() để reload dữ liệu sau khi xóa hết
                runOnUiThread(() -> {
                    authViewModel.getCart(email);

                    new AlertDialog.Builder(ConfirmOrderActivity.this)
                            .setTitle("Đặt hàng thành công 🎉")
                            .setMessage(message)
                            .setPositiveButton("Xem lịch sử", (dialog, which) -> {
                                startActivity(new Intent(ConfirmOrderActivity.this, ReceiptActivity.class));
                                finish();
                            })
                            .setNegativeButton("Về trang chủ", (dialog, which) -> {
                                startActivity(new Intent(ConfirmOrderActivity.this, MainActivity.class));
                                finish();
                            })
                            .show();
                });
            }).start();
        });

        orderviewModel.getErrorLiveData().observe(this, error -> {
            Toast.makeText(this, "❌ " + error, Toast.LENGTH_SHORT).show();
        });

        btnConfirm.setOnClickListener(v -> {
            String address = editAddress.getText().toString().trim();
            String phoneNumber = editPhone.getText().toString().trim();

            if (address.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            List<OrderItem> orderItems = new ArrayList<>();
            for (Item item : selectedItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(item.getProductId());
                orderItem.setProductName(item.getProductName());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSize(item.getSize());
                orderItem.setPrice(item.getPrice());
                orderItems.add(orderItem);
            }

            double totalPrice = 0;
            for (OrderItem item : orderItems) {
                totalPrice += item.getPrice() * item.getQuantity();
            }

            Order order = new Order();
            order.setEmail(email);
            order.setPhonenumber(phoneNumber);
            order.setAddress(address);
            order.setTotalPrice(totalPrice);
            order.setItems(orderItems);

            orderviewModel.createOrder(order);
        });
    }
}
