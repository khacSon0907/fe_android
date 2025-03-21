package com.example.myapplication.view.authentication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.LoginRequest;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.viewmodel.AuthViewModel;

public class Login extends AppCompatActivity {

    private TextView tvSignUp;
    private Button btnLogin;
    private EditText edtEmail, edtPassword;
    private AuthViewModel authViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(v -> loginUser());

        authViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String result) {
                new AlertDialog.Builder(Login.this)
                        .setTitle("Thông báo")
                        .setMessage(result)
                        .setPositiveButton("OK", (dialog, which) -> {
                            if (result.contains("Đăng nhập thành công!")) {
                                String email = edtEmail.getText().toString().trim();
                                saveLoginState(email); // Lưu trạng thái đăng nhập & email
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        })
                        .show();
            }
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Lỗi")
                    .setMessage("Vui lòng điền đầy đủ thông tin!")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);
        authViewModel.loginUser(loginRequest);
    }
    private void saveLoginState(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email); // Lưu email tài khoản
        editor.putBoolean("isLoggedIn", true); // Đánh dấu đã đăng nhập
        editor.apply();
    }

}