package com.example.myapplication.view.home;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.CategoryFragment;
import com.example.myapplication.fragments.ContactsFragment;
import com.example.myapplication.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Bật Edge-to-Edge (nếu cần)
        setContentView(R.layout.activity_main);

        // Xử lý padding cho hệ thống thanh điều hướng (system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Hiển thị Fragment mặc định (HomeFragment) khi khởi động
        loadFragment(new HomeFragment());

        // Xử lý BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.action_category:
                    loadFragment(new CategoryFragment());
                    return true;
                case R.id.action_contacts:
                    loadFragment(new ContactsFragment());
                    return true;
                case R.id.action_profile:
                    loadFragment(new ProfileFragment());
                    return true;
            }
            return false;
        });
    }

    // Phương thức để load Fragment
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}