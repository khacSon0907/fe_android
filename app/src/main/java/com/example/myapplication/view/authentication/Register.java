package com.example.myapplication.view.authentication;

import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


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

    private boolean isValidUsername(String username) {
        String usernamePattern = "^[a-zA-Z\\s]+$";
        return username.matches(usernamePattern) && username.length() > 4;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra số điện thoại có đúng 10 số và chỉ chứa chữ số
        return phoneNumber.length() == 10 && phoneNumber.matches("\\d+");
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hiển thị nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Xử lý sự kiện bấm vào mũi tên back
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            String email = edtMail.getText().toString().trim();
            String username = edtFullName.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String phonenumber = edtPhoneNumber.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || phonenumber.isEmpty()) {
                showAlertDialog("Lỗi", "Vui lòng điền đầy đủ thông tin!");
                return;
            }
            if (!isValidEmail(email)) {
                showAlertDialog("Lỗi", "Vui lòng nhập đúng định dạng email!");
                return;
            }
            if (!isValidPhoneNumber(phonenumber)) {
                showAlertDialog("Lỗi", "Số điện thoại phải có đúng 10 chữ số!");
                return;
            }
            if (!password.equals(confirmPassword)) {
                showAlertDialog("Lỗi", "Mật khẩu và xác nhận mật khẩu không khớp!");
                return;
            }
            if (!isValidUsername(username)) {
                showAlertDialog("Lỗi", "Tên không được chứa ký tự đặc biệt và phải dài hơn 6 ký tự!");
                return;
            }

            RegisterRequest registerRequest = new RegisterRequest(email, username, password, phonenumber);
            authViewModel.registerUser(registerRequest);
        });

        authViewModel.getRegisterResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String result) {
                // Hiển thị thông điệp đăng ký lên giao diện
                Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();

                // Kiểm tra nếu đăng ký thành công thì chuyển sang trang Login sau 1 giây
                if (result.equals("Đăng ký thành công!")) { // Thay bằng chuỗi thông báo từ ViewModel
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                            finish(); // Đóng activity hiện tại
                        }
                    }, 1000); // 1000 milliseconds = 1 giây
                }
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}