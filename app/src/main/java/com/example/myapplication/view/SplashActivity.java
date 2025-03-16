package com.example.myapplication.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Ánh xạ TextView và ImageView
        TextView welcomeText = findViewById(R.id.welcomeText);
        ImageView iconImage = findViewById(R.id.iconImage);

        // Load fade-in animation cho TextView
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        welcomeText.startAnimation(fadeInAnimation);

        iconImage.startAnimation(fadeInAnimation);


        // Tạo một Handler để chuyển sang màn hình chính sau 3 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển sang màn hình chính
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng SplashActivity để người dùng không thể quay lại
            }
        }, 2500); // Thời gian chờ: 3 giây
    }
}