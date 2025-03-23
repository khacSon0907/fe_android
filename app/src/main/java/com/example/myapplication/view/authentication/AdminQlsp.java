package com.example.myapplication.view.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.view.customAdapter.ProductAdapter;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class AdminQlsp extends AppCompatActivity {

    private ListView listviewProduct;
    private ArrayList<Product> productList;
    private ProductAdapter adapter;

    private Button btnAddProduct;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_qlsp);

        btnAddProduct   = findViewById(R.id.btnAddProduct);
        listviewProduct = findViewById(R.id.listviewProduct);
        // Tạo danh sách sản phẩm
        productList = new ArrayList<>();
        productList.add(new Product("Sản phẩm 1", 100000, R.drawable.product1));
        productList.add(new Product("Sản phẩm 2", 200000, R.drawable.product2));
        productList.add(new Product("Sản phẩm 3", 300000, R.drawable.product3));
        productList.add(new Product("Sản phẩm 1", 100000, R.drawable.product1));
        productList.add(new Product("Sản phẩm 2", 200000, R.drawable.product2));
        productList.add(new Product("Sản phẩm 3", 300000, R.drawable.product3));
        productList.add(new Product("Sản phẩm 1", 100000, R.drawable.product1));
        productList.add(new Product("Sản phẩm 2", 200000, R.drawable.product2));
        productList.add(new Product("Sản phẩm 3", 300000, R.drawable.product3));
        productList.add(new Product("Sản phẩm 1", 100000, R.drawable.product1));
        productList.add(new Product("Sản phẩm 2", 200000, R.drawable.product2));
        productList.add(new Product("Sản phẩm 3", 300000, R.drawable.product3));
        productList.add(new Product("Sản phẩm 1", 100000, R.drawable.product1));
        productList.add(new Product("Sản phẩm 2", 200000, R.drawable.product2));
        productList.add(new Product("Sản phẩm 3", 300000, R.drawable.product3));
        // Gán adapter cho ListView
        adapter = new ProductAdapter(this, productList);
        listviewProduct.setAdapter(adapter);


        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(AdminQlsp.this, AddProductActivity.class);
            startActivity(intent);
        });
    }
}
