package com.example.myapplication.view.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.RegisterRequest;
import com.example.myapplication.viewmodel.AuthViewModel;

public class Register extends AppCompatActivity {

    private EditText edtMail, edtFullName, edtPhoneNumber, edtPassword, edtConfirmPassword;
    private Button btnRegister;
    private TextView tvSignIn;
    private AuthViewModel authViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edtMail = findViewById(R.id.edtMail);
        edtFullName = findViewById(R.id.edtFullName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            // Ẩn bàn phím
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            // Lấy dữ liệu từ các trường nhập liệu
            String email = edtMail.getText().toString().trim();

            String username = edtFullName.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String phonenumber = edtPhoneNumber.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            // Kiểm tra dữ liệu nhập
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || phonenumber.isEmpty()) {
                showAlertDialog("Lỗi", "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            // Kiểm tra mật khẩu và xác nhận mật khẩu
            if (!password.equals(confirmPassword)) {
                showAlertDialog("Lỗi", "Mật khẩu và xác nhận mật khẩu không khớp!");
                return;
            }

            // Tạo đối tượng RegisterRequest
            RegisterRequest registerRequest = new RegisterRequest(email, username, password, phonenumber);

            // Gọi phương thức đăng ký từ ViewModel
            authViewModel.registerUser(registerRequest);
        });

        authViewModel.getRegisterResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String result) {
                // Hiển thị thông điệp đăng ký lên giao diện
                Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm hiển thị AlertDialog
    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}