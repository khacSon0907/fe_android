package com.example.myapplication.view.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Item;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends BaseAdapter implements ListAdapter {
    private Context context;
    private List<Item> itemList;

    public CartAdapter(Context context, Cart cart) {
        this.context = context;
        this.itemList = cart.getItems();
    }

    @Override
    public int getCount() {
        return itemList.size();
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
        TextView txtTenGioHang, txtGiaGioHang, txtSoLuong;
        ImageView imgGioHang;
        Button btnMinus, btnPlus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_cart, parent, false);

            viewHolder.imgGioHang = convertView.findViewById(R.id.imageViewProduct);
            viewHolder.txtTenGioHang = convertView.findViewById(R.id.textviewProductName);
            viewHolder.txtGiaGioHang = convertView.findViewById(R.id.textviewProductPrice);
            viewHolder.txtSoLuong = convertView.findViewById(R.id.textviewQuantity);
            viewHolder.btnMinus = convertView.findViewById(R.id.buttonminus);
            viewHolder.btnPlus = convertView.findViewById(R.id.buttonplus);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item item = itemList.get(position);
        viewHolder.txtTenGioHang.setText(item.getProductName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText(decimalFormat.format(item.getPrice()) + " Đ");

        viewHolder.txtSoLuong.setText(String.valueOf(item.getQuantity()));

        // Xử lý sự kiện tăng/giảm số lượng
        viewHolder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyDataSetChanged();
        });

        viewHolder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
