package com.example.myapplication.view.admin;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.ProductAdmin;
import com.example.myapplication.view.customAdapter.*;
import com.example.myapplication.viewmodel.ProductViewModel;
import java.util.ArrayList;
import java.util.List;

public class AdminQlsp extends AppCompatActivity implements ProductAdapter.OnProductDeleteListener {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductAdmin> productList = new ArrayList<>();
    private ProductViewModel productViewModel;
    private Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_qlsp);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminQlsp.this, Admin.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.listviewProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnAddProduct = findViewById(R.id.btnAddProduct);

        adapter = new ProductAdapter(this, productList, this); // this là Listener
        recyclerView.setAdapter(adapter);

        btnAddProduct.setOnClickListener(v ->
                startActivity(new Intent(AdminQlsp.this, AddProductActivity.class)));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.getProductList().observe(this, products -> {
            productList.clear();
            productList.addAll(products);
            adapter.notifyDataSetChanged();
        });

        productViewModel.getErrorMessage().observe(this, errorMsg ->
                Toast.makeText(AdminQlsp.this, errorMsg, Toast.LENGTH_SHORT).show());

        productViewModel.getSuccessMessage().observe(this, successMsg ->
                Toast.makeText(AdminQlsp.this, successMsg, Toast.LENGTH_SHORT).show());

        productViewModel.fetchProducts();
    }

    @Override
    public void onProductDelete(String productId) {
        productViewModel.deleteProduct(productId);
    }
}
