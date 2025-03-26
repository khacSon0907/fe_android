package com.example.myapplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar; // Import đúng

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.model.Photo;
import com.example.myapplication.model.PhotoViewPager2Adapter;
import com.example.myapplication.model.ProductAdmin;
import com.example.myapplication.view.home.CartActivity;
import com.example.myapplication.view.customAdapter.UserProductAdapter;
import com.example.myapplication.viewmodel.ProductViewModel;


import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private RecyclerView recyclerView;

    private UserProductAdapter userProductAdapter;
    private List<ProductAdmin> productList = new ArrayList<>();
    private ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_cart) {
                startActivity(new Intent(getActivity(), CartActivity.class));
                return true;
            }
            return false;
        });

        // Banner slider
        mViewPager2 = view.findViewById(R.id.view_pager_2);
        mCircleIndicator3 = view.findViewById(R.id.circle_indicator_3);
        PhotoViewPager2Adapter adapter = new PhotoViewPager2Adapter(getListPhoto());
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);

        // RecyclerView sản phẩm
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        userProductAdapter = new UserProductAdapter(getContext(), productList);
        recyclerView.setAdapter(userProductAdapter);

        // ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            productList.clear();
            productList.addAll(products);
            userProductAdapter.notifyDataSetChanged();
        });

        productViewModel.getErrorMessage().observe(getViewLifecycleOwner(), err ->
                Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show());

        productViewModel.fetchProducts(); // Gọi API

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
}

