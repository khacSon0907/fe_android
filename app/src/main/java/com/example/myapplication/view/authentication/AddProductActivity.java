package com.example.myapplication.view.authentication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.view.customAdapter.Brands;
import com.example.myapplication.view.customAdapter.BrandsAdapter;
import com.example.myapplication.view.customAdapter.Category;
import com.example.myapplication.view.customAdapter.CategoryAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri imageUri;
    private Spinner spin_catgory,spin_brand;
    private CategoryAdapter categoryAdapter;

    private BrandsAdapter brandsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        spin_brand = findViewById(R.id.spin_brand);
        spin_catgory = findViewById(R.id.spin_catgory);

        categoryAdapter = new CategoryAdapter(this,R.layout.item_selected,getListCategory());
        brandsAdapter = new BrandsAdapter(this,R.layout.item_selected_brand,getListBrands());

        spin_brand.setAdapter(brandsAdapter);
        spin_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddProductActivity.this,brandsAdapter.getItem(position).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spin_catgory.setAdapter(categoryAdapter);
        spin_catgory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddProductActivity.this,categoryAdapter.getItem(position).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        imageView = findViewById(R.id.imageView);
        Button btnUpload = findViewById(R.id.btnUpload);

        btnUpload.setOnClickListener(v -> openGallery());
    }
    private List<Category>  getListCategory(){
        List<Category> list = new ArrayList<>();
        list.add(new Category(" Chọn loại sản phẩm :  "));
        list.add(new Category(" Quần "));
        list.add(new Category(" Áo  "));
        list.add(new Category(" Giày "));
        list.add(new Category(" Dép "));
        return  list;
    }
    private List<Brands>  getListBrands(){
        List<Brands> list = new ArrayList<>();
        list.add(new Brands("chọn Hãng "));
        list.add(new Brands("Nike "));
        list.add(new Brands("Adidas "));
        list.add(new Brands("Gucci "));

        return  list;
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
