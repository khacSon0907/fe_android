package com.example.myapplication.view.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.ProductAdmin;
import com.example.myapplication.viewmodel.AuthViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productImage;
    private TextView productName, productPrice, productDescription, productCategory, productBrand;
    private ImageButton btnBack, btnGoToCart;
    private LinearLayout sizeContainer;
    private Button btnAddToCart;
    private String selectedSize = null;
    private AuthViewModel authViewModel;
    private ProductAdmin product;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

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

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        Intent intent = getIntent();
        product = new ProductAdmin(
                intent.getStringExtra("id"),
                intent.getStringExtra("name"),
                Double.parseDouble(intent.getStringExtra("price")),
                intent.getStringExtra("description"),
                intent.getStringExtra("category"),
                intent.getStringExtra("brand"),
                intent.getStringExtra("imageUrl")
        );

        productName.setText(product.getName());
        productPrice.setText(product.getPrice() + " VNĐ");
        productDescription.setText(product.getDescription());
        productCategory.setText("Loại: " + product.getCategory());
        productBrand.setText("Hãng: " + product.getBrand());

        Glide.with(this)
                .load("http://10.0.2.2:8080" + product.getImageUrl())
                .placeholder(R.drawable.product1)
                .into(productImage);

        loadSizeOptionsByCategory(product.getCategory());

        btnBack.setOnClickListener(v -> finish());

        btnGoToCart.setOnClickListener(v -> startActivity(new Intent(ProductDetailActivity.this, CartActivity.class)));

        btnAddToCart.setOnClickListener(v -> {
            if (selectedSize == null) {
                Toast.makeText(ProductDetailActivity.this, "Vui lòng chọn size!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String email = sharedPreferences.getString("email", null);

            if (email == null) {
                Toast.makeText(ProductDetailActivity.this, "Bạn cần đăng nhập để thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            Item item = new Item(
                    product.getId(),
                    product.getName() + " - Size " + selectedSize,
                    1,
                    product.getPrice(),
                    selectedSize,
                    product.getImageUrl()// TRUYỀN size ở đây
            );
            authViewModel.addToCart(email, item);

            authViewModel.getSuccessLiveData().observe(ProductDetailActivity.this, message -> {
                if (message != null) {
                    Toast.makeText(ProductDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });

            authViewModel.getErrorLiveData().observe(ProductDetailActivity.this, error -> {
                if (error != null) {
                    Toast.makeText(ProductDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    private void loadSizeOptionsByCategory(String category) {
        List<String> sizes = new ArrayList<>();
        if (category == null) return;

        category = category.toLowerCase();

        if (category.contains("áo")) {
            sizes = Arrays.asList("S", "M", "L", "XL");
        } else if (category.contains("quần")) {
            for (int i = 25; i <= 32; i++) sizes.add(String.valueOf(i));
        } else if (category.contains("giày") || category.contains("dép")) {
            for (int i = 35; i <= 42; i++) sizes.add(String.valueOf(i));
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
            tv.setBackgroundResource(tv.getText().toString().equals(selected)
                    ? R.drawable.size_selected : R.drawable.size_unselected);
        }
    }
}
