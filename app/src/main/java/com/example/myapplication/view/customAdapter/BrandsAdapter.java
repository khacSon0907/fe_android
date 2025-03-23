package com.example.myapplication.view.customAdapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.List;

public class BrandsAdapter extends ArrayAdapter<Brands> {
    public BrandsAdapter(@NonNull Context context, int resource, @NonNull List<Brands> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_brand,parent,false);
        TextView tvSelected = convertView.findViewById(R.id.tv_selected_brand);

        Brands brands = this.getItem(position);
        if(brands != null){
            tvSelected.setText(brands.getName());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand ,parent,false);
        TextView tvCategory = convertView.findViewById(R.id.tv_brand);

        Brands brands = this.getItem(position);
        if(brands != null){
            tvCategory.setText(brands.getName());
        }

        return convertView;
    }
}
