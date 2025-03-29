package com.example.myapplication.view.receipt;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.OrderItem;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class ReceiptDetailActivity extends AppCompatActivity {

    private TableLayout tableContent;
    private TextView tvTotalPrice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_detail);

        tableContent = findViewById(R.id.tableContent);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        String orderJson = getIntent().getStringExtra("order_json");
        Order order = new Gson().fromJson(orderJson, Order.class);

        double total = 0;
        DecimalFormat formatter = new DecimalFormat("#,###");

        for (OrderItem item : order.getItems()) {
            TableRow row = new TableRow(this);

            // Tạo từng cột
            TextView name = createCell(item.getProductName());
            TextView size = createCell(item.getSize());
            TextView qty = createCell(String.valueOf(item.getQuantity()));
            TextView price = createCell(formatter.format(item.getPrice()) + "đ");

            // Add vào row
            row.addView(name);
            row.addView(size);
            row.addView(qty);
            row.addView(price);

            // Add row vào bảng
            tableContent.addView(row);

            total += item.getPrice() * item.getQuantity();
        }

        tvTotalPrice.setText("Tổng cộng: " + formatter.format(total) + "đ");
    }

    private TextView createCell(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(8, 8, 8, 8);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundResource(R.drawable.border_cell);
        tv.setLayoutParams(new TableRow.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f // chia đều
        ));
        return tv;
    }

}
