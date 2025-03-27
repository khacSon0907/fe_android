// ✅ File 1: CartAdapter.java (Đã xử lý checkbox hoàn chỉnh để chọn sản phẩm tính tiền)

package com.example.myapplication.view.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
        CheckBox cboxSelect;
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
            holder.cboxSelect = convertView.findViewById(R.id.checkboxSelect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = itemList.get(position);

        holder.txtTen.setText(item.getProductName());
        holder.txtSoLuong.setText(String.valueOf(item.getQuantity()));

        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.txtGia.setText(formatter.format(item.getPrice()) + " Đ");

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

        holder.btnDelete.setOnClickListener(v -> {
            authViewModel.deleteCartItem(email, item.getProductId(), item.getSize());
        });

        holder.btnPlus.setOnClickListener(v -> {
            authViewModel.increaseQuantity(email, item.getProductId(), item.getSize());
        });

        holder.btnMinus.setEnabled(item.getQuantity() > 1);
        holder.btnMinus.setOnClickListener(v -> {
            authViewModel.decreaseQuantity(email, item.getProductId(), item.getSize());
        });

        // Hiển thị trạng thái checkbox
        holder.cboxSelect.setChecked(item.isSelected());

        // Xử lý sự kiện chọn checkbox
        holder.cboxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setSelected(isChecked);
            if (context instanceof com.example.myapplication.view.home.CartActivity) {
                ((com.example.myapplication.view.home.CartActivity) context).recalculateTotal();
            }
        });

        return convertView;
    }
}
