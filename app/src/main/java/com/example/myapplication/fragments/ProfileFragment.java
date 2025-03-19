package com.example.myapplication.fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ProfileOption;
import com.example.myapplication.view.ProfileAdapter;
import com.example.myapplication.view.authentication.Login;
import com.example.myapplication.view.authentication.Register;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    private List<ProfileOption> options;

    private Button btn_login , btn_register;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.recycler_profile_options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_login = view.findViewById(R.id.btn_login);
        btn_register = view.findViewById(R.id.btn_register);

        options = new ArrayList<>();
        options.add(new ProfileOption(R.drawable.ic_phone, "Hỗ Trợ","1900 545 403"));
        options.add(new ProfileOption(R.drawable.ic_email, "Email","support@fado.vn"));
        options.add(new ProfileOption(R.drawable.ic_order, "Đơn hàng","Kiểm tra đơn hàng"));
        options.add(new ProfileOption(R.drawable.ic_language, "Ngôn ngữ","Đổi ngôn ngữ"));
        options.add(new ProfileOption(R.drawable.ic_info, "Thông tin","Thông tin ứng dụng"));

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

        return view;
    }
}
