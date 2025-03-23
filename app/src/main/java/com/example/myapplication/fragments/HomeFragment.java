package com.example.myapplication.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.Photo;
import com.example.myapplication.model.PhotoViewPager2Adapter;
import com.example.myapplication.view.customAdapter.ProductAdapter;
import com.example.myapplication.view.customAdapter.UserProductAdapter;


import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<Photo> mListPhoto;
    private RecyclerView recyclerView;

    private UserProductAdapter userProductAdapter;
    private List<Product> productList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ ViewPager2 và CircleIndicator3

        mViewPager2 = view.findViewById(R.id.view_pager_2);
        mCircleIndicator3 = view.findViewById(R.id.circle_indicator_3);

        // Khởi tạo danh sách ảnh
        mListPhoto = getListPhoto();
        PhotoViewPager2Adapter adapter = new PhotoViewPager2Adapter(mListPhoto);

        // Thiết lập Adapter cho ViewPager2
        mViewPager2.setAdapter(adapter);

        // Kết nối CircleIndicator3 với ViewPager2
        mCircleIndicator3.setViewPager(mViewPager2);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Chia danh sách sản phẩm thành 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20); // Tăng bộ nhớ cache để tránh lỗi hiển thị
        gridLayoutManager.setAutoMeasureEnabled(true);
        productList = getProductList();
        userProductAdapter = new UserProductAdapter(getContext(), productList);
        recyclerView.setAdapter(userProductAdapter);
        return view;

    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img_1));
        list.add(new Photo(R.drawable.img_2));
        list.add(new Photo(R.drawable.img_3));
        list.add(new Photo(R.drawable.img_4));
        return list;
    }
    private List<Product> getProductList() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("Sản phẩm 1", 100000, R.drawable.product1));
        list.add(new Product("Sản phẩm 2", 200000, R.drawable.product2));
        list.add(new Product("Sản phẩm 3", 300000, R.drawable.product3));
        list.add(new Product("Sản phẩm 4", 200000, R.drawable.img_4));
        list.add(new Product("Sản phẩm 5", 700000, R.drawable.img_1));
        list.add(new Product("Sản phẩm 6", 900000, R.drawable.img_2));
        list.add(new Product("Sản phẩm 5", 700000, R.drawable.img_1));
        list.add(new Product("Sản phẩm 6", 900000, R.drawable.img_2));
        list.add(new Product("Sản phẩm 5", 700000, R.drawable.img_1));
        list.add(new Product("Sản phẩm 6", 900000, R.drawable.img_2));
        return list;
    }
}

