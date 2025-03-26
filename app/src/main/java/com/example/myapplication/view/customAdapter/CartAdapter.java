package com.example.myapplication.view.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Item;
import com.example.myapplication.viewmodel.AuthViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    private final Context context;
    private final List<Item> itemList;
    private final AuthViewModel authViewModel;
    private final String email;

    public CartAdapter(Context context, Cart cart, AuthViewModel authViewModel, String email) {
        this.context = context;
        this.itemList = cart.getItems();
        this.authViewModel = authViewModel;
        this.email = email;
    }

    @Override
    public int getCount() {
        return itemList != null ? itemList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView txtTen, txtGia, txtSoLuong;
        ImageView img;
        Button btnPlus, btnMinus, btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.imageViewProduct);
            holder.txtTen = convertView.findViewById(R.id.textviewProductName);
            holder.txtGia = convertView.findViewById(R.id.textviewProductPrice);
            holder.txtSoLuong = convertView.findViewById(R.id.textviewQuantity);
            holder.btnMinus = convertView.findViewById(R.id.buttonminus);
            holder.btnPlus = convertView.findViewById(R.id.buttonplus);
            holder.btnDelete = convertView.findViewById(R.id.btnDeleteCart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Item item = itemList.get(position);

        holder.btnDelete.setOnClickListener(v -> {
            authViewModel.deleteCartItem(email, item.getProductId(), item.getSize());
            // KHÔNG xoá local, đợi LiveData cập nhật lại từ backend
        });

        holder.txtTen.setText(item.getProductName());
        holder.txtSoLuong.setText(String.valueOf(item.getQuantity()));

        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.txtGia.setText(formatter.format(item.getPrice()) + " Đ");

        // Load ảnh
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            String fullImageUrl = "http://10.0.2.2:8080" + item.getImageUrl();
            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.product1)
                    .error(R.drawable.product2)
                    .into(holder.img);
        } else {
            holder.img.setImageResource(R.drawable.product1);
        }

        // Tăng số lượng
        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyDataSetChanged();
        });

        // Giảm số lượng
        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyDataSetChanged();
            }
        });



        return convertView;
    }
}
