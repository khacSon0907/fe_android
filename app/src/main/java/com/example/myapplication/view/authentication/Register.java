package com.example.myapplication.view.authentication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.RegisterRequest;
import com.example.myapplication.viewmodel.AuthViewModel;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

public class Register extends AppCompatActivity {

    private EditText edtMail, edtFullName, edtPhoneNumber,edtPassword,edtConfirmPassword;
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

        btnRegister.setOnClickListener(v -> {
            // Ẩn bàn phím
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            // Lấy dữ liệu từ các trường nhập liệu
            String email = edtMail.getText().toString();
            String username = edtFullName.getText().toString();
            String password = edtPassword.getText().toString();
            String phonenumber = edtPhoneNumber.getText().toString();

            // Kiểm tra dữ liệu nhập liệu
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || phonenumber.isEmpty()) {
                tvSignIn.setText("Vui lòng điền đầy đủ thông tin!");
                return; // Dừng lại nếu thiếu thông tin
            }

            // Kiểm tra mật khẩu và xác nhận mật khẩu
            String confirmPassword = edtConfirmPassword.getText().toString();
            if (!password.equals(confirmPassword)) {
                tvSignIn.setText("Mật khẩu và xác nhận mật khẩu không khớp!");
                return; // Dừng lại nếu mật khẩu không khớp
            }

            // Tạo đối tượng RegisterRequest
            RegisterRequest registerRequest = new RegisterRequest(email, username, password, phonenumber);

            // Gọi phương thức đăng ký từ ViewModel
            authViewModel.registerUser(registerRequest);
        });
    }
}