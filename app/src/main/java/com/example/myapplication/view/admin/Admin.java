package com.example.myapplication.view.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.view.authentication.Login;
import com.example.myapplication.view.home.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Admin extends AppCompatActivity {

    private Button btnLogout,btnAdd ,btn_nextHome;
    private EditText editAdd;

    private ListView lisviewQladmin;
    private ArrayList<String> listNameQl = new ArrayList<>(Arrays.asList("QUẢN LÝ SẢN PHẨM","QUẢN LÝ HOÁ ĐƠN","QUẢN LÝ TÀI KHOẢN"));

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        btn_nextHome = findViewById(R.id.btn_nextHome);
        editAdd = findViewById(R.id.editAdd);
        btnAdd = findViewById(R.id.btnAdd);
        lisviewQladmin = findViewById(R.id.lisviewQladmin);
        btnLogout = findViewById(R.id.btnLogout);


        btn_nextHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(Admin.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

            // Xóa dữ liệu đăng nhập
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Xóa toàn bộ thông tin đăng nhập
            editor.apply();

            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(Admin.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa tất cả activity trước đó
            startActivity(intent);
            finish(); // Kết thúc activity hiện tại
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Admin.this, R.layout.item_admin_qlsp, R.id.txtItem, listNameQl);


        lisviewQladmin.setAdapter(arrayAdapter);

        lisviewQladmin.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = listNameQl.get(position);

            if (selectedItem.equals("QUẢN LÝ SẢN PHẨM")) {
                Intent intent = new Intent(Admin.this, AdminQlsp.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = editAdd.getText().toString();
                listNameQl.add(ten);
                arrayAdapter.notifyDataSetChanged();
                editAdd.setText("");
            }
        });
    }
}
