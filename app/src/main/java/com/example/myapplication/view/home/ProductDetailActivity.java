package com.example.myapplication.view.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productDescription, productCategory, productBrand;
    private ImageButton btnBack, btnGoToCart;

    private TextView tvSizeLabel ;
    private String selectedSize = null;
    private LinearLayout sizeContainer;

    private Button btnAddToCart ;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail); // ← tên XML chi tiết

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        productCategory = findViewById(R.id.productCategory);
        productBrand = findViewById(R.id.productBrand);
        btnBack = findViewById(R.id.btnBack);
        btnGoToCart = findViewById(R.id.btnGoToCart);
        sizeContainer = findViewById(R.id.sizeContainer);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        // Nhận dữ liệu
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        String description = intent.getStringExtra("description");
        String category = intent.getStringExtra("category");
        loadSizeOptionsByCategory(category);
        String brand = intent.getStringExtra("brand");
        String imageUrl = intent.getStringExtra("imageUrl");

        // Hiển thị dữ liệu
        productName.setText(name);
        productPrice.setText(price + " VNĐ");
        productDescription.setText(description);
        productCategory.setText("Loại: " + category);
        productBrand.setText("Hãng: " + brand);

        Glide.with(this)
                .load("http://10.0.2.2:8080" + imageUrl)
                .placeholder(R.drawable.product1)
                .into(productImage);

        // Quay lại
        btnBack.setOnClickListener(v -> finish());

        // Đến giỏ hàng
        btnGoToCart.setOnClickListener(v -> {
            Intent goToCart = new Intent(ProductDetailActivity.this,CartActivity.class);
            startActivity(goToCart);
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSize == null) {
                    Toast.makeText(ProductDetailActivity.this, "Vui lòng chọn size!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    private void loadSizeOptionsByCategory(String category) {
        List<String> sizes = new ArrayList<>();

        if (category == null) return;

        category = category.toLowerCase();

        if (category.contains("áo")) {
            sizes = Arrays.asList("S", "M", "L", "XL");
        } else if (category.contains("quần")) {
            for (int i = 25; i <= 32; i++) {
                sizes.add(String.valueOf(i));
            }
        } else if (category.contains("giày") || category.contains("dép")) {
            for (int i = 35; i <= 42; i++) {
                sizes.add(String.valueOf(i));
            }
        }

        for (String size : sizes) {
            TextView tv = new TextView(this);
            tv.setText(size);
            tv.setPadding(30, 20, 30, 20);
            tv.setBackgroundResource(R.drawable.size_unselected);
            tv.setTextColor(Color.BLACK);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(16, 0, 0, 0);
            tv.setLayoutParams(params);

            // Sự kiện chọn size
            tv.setOnClickListener(v -> {
                selectedSize = size;
                updateSizeUI(sizeContainer, size);
            });

            sizeContainer.addView(tv);
        }
    }
    private void updateSizeUI(LinearLayout container, String selected) {
        for (int i = 0; i < container.getChildCount(); i++) {
            TextView tv = (TextView) container.getChildAt(i);
            if (tv.getText().toString().equals(selected)) {
                tv.setBackgroundResource(R.drawable.size_selected);
            } else {
                tv.setBackgroundResource(R.drawable.size_unselected);
            }
        }
    }

}