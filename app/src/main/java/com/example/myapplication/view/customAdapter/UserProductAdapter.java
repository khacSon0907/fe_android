package com.example.myapplication.view.customAdapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductAdmin;

import java.text.DecimalFormat;
import java.util.List;

public class UserProductAdapter extends RecyclerView.Adapter<UserProductAdapter.ViewHolder> {
    private Context context;
    private List<ProductAdmin> productList;

    public UserProductAdapter(Context context, List<ProductAdmin> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductAdmin product = productList.get(position);

        holder.productName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(product.getPrice());
        holder.productPrice.setText(formattedPrice + " VNĐ");

        Glide.with(context)
                .load("http://10.0.2.2:8080" + product.getImageUrl())
                .placeholder(R.drawable.product1)
                .error(R.drawable.product2)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
