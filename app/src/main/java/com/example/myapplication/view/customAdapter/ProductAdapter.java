package com.example.myapplication.view.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.BaseAdapter;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> productList;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        // Lấy dữ liệu của sản phẩm
        Product product = productList.get(position);

        // Ánh xạ UI
        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        // Set dữ liệu vào giao diện
        productImage.setImageResource(product.getImageResource());
        productName.setText(product.getName());
        productPrice.setText(product.getPrice() + " VNĐ");

        // Xử lý sự kiện nút Sửa
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(context, "Sửa sản phẩm: " + product.getName(), Toast.LENGTH_SHORT).show();
        });

        // Xử lý sự kiện nút Xóa
        btnDelete.setOnClickListener(v -> {
            productList.remove(position); // Xóa sản phẩm khỏi danh sách
            notifyDataSetChanged(); // Cập nhật lại danh sách
            Toast.makeText(context, "Xóa sản phẩm: " + product.getName(), Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
