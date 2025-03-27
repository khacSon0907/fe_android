package com.example.myapplication.view.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;

import java.text.DecimalFormat;
import java.util.List;

public class SelectedItemAdapter extends BaseAdapter {

    private final Context context;
    private final List<Item> items;
    private final LayoutInflater inflater;

    public SelectedItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (items != null) ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (items != null && position < items.size()) ? items.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView txtName, txtSize, txtQuantity, txtPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_selected_order, parent, false);
            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.txtProductName);
            holder.txtSize = convertView.findViewById(R.id.txtSize);
            holder.txtQuantity = convertView.findViewById(R.id.txtQuantity);
            holder.txtPrice = convertView.findViewById(R.id.txtPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = items.get(position);

        holder.txtName.setText(item.getProductName() != null ? item.getProductName() : "Tên không xác định");
        holder.txtSize.setText("Size: " + (item.getSize() != null ? item.getSize() : "-"));
        holder.txtQuantity.setText("Số lượng: " + item.getQuantity());

        DecimalFormat formatter = new DecimalFormat("#,###");
        double itemTotalPrice = item.getPrice() * item.getQuantity();
        holder.txtPrice.setText(formatter.format(itemTotalPrice) + " Đ");

        return convertView;
    }
}
