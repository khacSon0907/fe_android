package com.example.myapplication.view.authentication;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.ProductAdmin;
import com.example.myapplication.view.customAdapter.Brands;
import com.example.myapplication.view.customAdapter.BrandsAdapter;
import com.example.myapplication.view.customAdapter.Category;
import com.example.myapplication.view.customAdapter.CategoryAdapter;
import com.example.myapplication.viewmodel.ProductViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri imageUri;
    private Spinner spin_category, spin_brand;
    private EditText edtTenSanPham, edtGiaSanPham, edtMoTaSp;
    private Button btn_save, btnUpload,btn_back;
    private ProductViewModel productViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        // Khởi tạo các View
        imageView = findViewById(R.id.imageView);
        btnUpload = findViewById(R.id.btnUpload);
        btn_save = findViewById(R.id.btn_save);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtGiaSanPham = findViewById(R.id.edtGiaSanPham);
        edtMoTaSp = findViewById(R.id.edtMoTaSp);
        spin_category = findViewById(R.id.spin_catgory);
        spin_brand = findViewById(R.id.spin_brand);
        btn_back = findViewById(R.id.btn_back);

        // Khởi tạo ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Thiết lập Adapter cho Spinner
        spin_category.setAdapter(new CategoryAdapter(this, R.layout.item_selected, getListCategory()));
        spin_brand.setAdapter(new BrandsAdapter(this, R.layout.item_selected_brand, getListBrands()));

        // Mở thư viện ảnh khi nhấn vào nút Upload
        btnUpload.setOnClickListener(v -> openGallery());

        // Lưu sản phẩm khi nhấn nút Save
        btn_save.setOnClickListener(v -> saveProduct());

        // Quan sát LiveData để hiển thị thông báo khi tạo sản phẩm thành công
        productViewModel.getSuccessMessage().observe(this, result -> {
            if (result != null) {
                showSuccessAlert(result);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, AdminQlsp.class);
                startActivity(intent);
            }
        });
    }

    // Mở thư viện để chọn ảnh
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

    private void saveProduct() {
        String name = edtTenSanPham.getText().toString().trim();
        String price = edtGiaSanPham.getText().toString().trim();
        String description = edtMoTaSp.getText().toString().trim();

        Category selectedCategory = (Category) spin_category.getSelectedItem();
        Brands selectedBrand = (Brands) spin_brand.getSelectedItem();

        String category = selectedCategory.getName();
        String brand = selectedBrand.getName();

        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy đường dẫn ảnh
        String imagePath = getRealPathFromURI(imageUri);
        if (imagePath == null) {
            Toast.makeText(this, "Lỗi lấy đường dẫn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        File imageFile = new File(imagePath);
        ProductAdmin product =  new ProductAdmin(name, Double.parseDouble(price), description, category, brand);

        // Gửi sản phẩm đến ViewModel để xử lý
        productViewModel.createProduct(product, imageFile);
    }

    // Hiển thị thông báo thành công
    private void showSuccessAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Thành công")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    finish(); // Đóng Activity sau khi tạo sản phẩm thành công
                })
                .show();
    }

    // Lấy danh sách loại sản phẩm
    private List<Category> getListCategory() {
        List<Category> list = new ArrayList<>();
        list.add(new Category("Chọn loại sản phẩm"));
        list.add(new Category("Quần"));
        list.add(new Category("Áo"));
        list.add(new Category("Giày"));
        list.add(new Category("Dép"));
        return list;
    }

    // Lấy danh sách thương hiệu
    private List<Brands> getListBrands() {
        List<Brands> list = new ArrayList<>();
        list.add(new Brands("Chọn Hãng"));
        list.add(new Brands("Nike"));
        list.add(new Brands("Adidas"));
        list.add(new Brands("Gucci"));
        return list;
    }

    // Lấy đường dẫn ảnh từ URI
    private String getRealPathFromURI(Uri uri) {
        if (uri == null) return null;

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getPathFromContentUri(uri);
        } else {
            return uri.getPath();
        }
    }

    private String getPathFromContentUri(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            String fileName = cursor.getString(nameIndex);
            cursor.close();

            File file = new File(getCacheDir(), fileName);
            try {
                InputStream inputStream = contentResolver.openInputStream(uri);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return file.getAbsolutePath();
        }
        return null;
    }
}
