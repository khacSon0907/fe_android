package com.example.myapplication.view.customAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.ProductAdmin;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnProductDeleteListener {
        void onProductDelete(String productId);
    }

    private Context context;
    private List<ProductAdmin> productList;
    private OnProductDeleteListener deleteListener;

    public ProductAdapter(Context context, List<ProductAdmin> productList, OnProductDeleteListener listener) {
        this.context = context;
        this.productList = productList;
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductAdmin product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + " VNĐ");

        Glide.with(context)
                .load("http://10.0.2.2:8080" + product.getImageUrl())
                .placeholder(R.drawable.product1)
                .error(R.drawable.product2)
                .into(holder.productImage);

        holder.btnEdit.setOnClickListener(v ->
                Toast.makeText(context, "Sửa sản phẩm: " + product.getName(), Toast.LENGTH_SHORT).show());

        holder.btnDelete.setOnClickListener(v -> {
            deleteListener.onProductDelete(product.getId()); // Gọi interface để xóa
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;
        Button btnEdit, btnDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}