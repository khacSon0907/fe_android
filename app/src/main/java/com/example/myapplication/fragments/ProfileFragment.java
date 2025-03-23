package com.example.myapplication.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ProfileOption;
import com.example.myapplication.view.customAdapter.ProfileAdapter;
import com.example.myapplication.view.authentication.Login;
import com.example.myapplication.view.authentication.Register;
import com.example.myapplication.viewmodel.AuthViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private AuthViewModel authViewModel;

    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    private List<ProfileOption> options;

    private Button btn_login, btn_register, btnLogout;
    private TextView tvUserEmail, tvUserName;

    private LinearLayout layoutAuth, layoutUserInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.recycler_profile_options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        btn_login = view.findViewById(R.id.btn_login);
        btn_register = view.findViewById(R.id.btn_register);
        btnLogout = view.findViewById(R.id.btnLogout);

        layoutAuth = view.findViewById(R.id.layoutAuth);
        layoutUserInfo = view.findViewById(R.id.layoutUserInfo);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvUserName = view.findViewById(R.id.tvUserName);

        options = new ArrayList<>();
        options.add(new ProfileOption(R.drawable.ic_phone, "Hỗ Trợ", "1900 545 403"));
        options.add(new ProfileOption(R.drawable.ic_email, "Email", "support@fado.vn"));
        options.add(new ProfileOption(R.drawable.ic_order, "Đơn hàng", "Kiểm tra đơn hàng"));
        options.add(new ProfileOption(R.drawable.ic_language, "Ngôn ngữ", "Đổi ngôn ngữ"));
        options.add(new ProfileOption(R.drawable.ic_info, "Thông tin", "Thông tin ứng dụng"));

        adapter = new ProfileAdapter(options, new ProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProfileOption option) {
                // Xử lý khi item được nhấn
            }
        });


        recyclerView.setAdapter(adapter);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Register.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Login.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Xóa toàn bộ thông tin đăng nhập
            editor.apply();


            layoutAuth.setVisibility(View.VISIBLE); // Hiện giao diện đăng nhập
            layoutUserInfo.setVisibility(View.GONE); // Ẩn thông tin user
        });
        checkLoginStatus();

// Quan sát dữ liệu người dùng từ ViewModel
        authViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // Cập nhật thông tin người dùng vào UI
                tvUserName.setText("Name: " + user.getUsername());
                layoutAuth.setVisibility(View.GONE);
                layoutUserInfo.setVisibility(View.VISIBLE);
            } else {
                tvUserEmail.setText("Thông tin người dùng không có sẵn.");
            }
        });
        return view;
    }

    private void checkLoginStatus() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        String email = sharedPreferences.getString("email", "");

        if (isLoggedIn) {
            layoutAuth.setVisibility(View.GONE); // Ẩn giao diện đăng ký/đăng nhập
            layoutUserInfo.setVisibility(View.VISIBLE); // Hiện giao diện user

            tvUserEmail.setText("Email: " + email);

            getUserData(email);
        } else {
            layoutAuth.setVisibility(View.VISIBLE);
            layoutUserInfo.setVisibility(View.GONE);

        }
    }

    private void getUserData(String email) {

            if (email != null && !email.isEmpty()) {
                authViewModel.getUserbyEmail(email); // Gọi phương thức để lấy thông tin người dùng
            }
    }

}
