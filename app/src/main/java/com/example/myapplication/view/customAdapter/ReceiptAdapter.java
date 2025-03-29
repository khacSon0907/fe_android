package com.example.myapplication.view.customAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Order;

import java.text.DecimalFormat;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.OrderViewHolder> {

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
        void onCancelOrderClick(String orderId);
    }

    private String status = " ";

    private List<Order> orderList;
    private Context context;
    private OnOrderClickListener listener;

    public ReceiptAdapter(List<Order> orderList, Context context, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_receipt, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtDate.setText("Ngày tạo: " + order.getCreatedAt().toString());

        if(order.getStatus().equals("pending")){
             status = "Chưa xác nhận";
        }
        else {
            status = " đã xác nhận";
        }
        holder.txtStatus.setText("Trạng thái: " + status);
        holder.txtPhone.setText("SĐT: " + order.getPhonenumber());
        holder.txtAddress.setText("Địa chỉ: " + order.getAddress());

        DecimalFormat formatter = new DecimalFormat("#,###");

        holder.txtTotal.setText("Tổng tiền: " + formatter.format(order.getTotalPrice()) + "đ");

        holder.btnCancel.setOnClickListener(v -> {
            listener.onCancelOrderClick(order.getId());
        });

        holder.itemView.setOnClickListener(v -> {
            listener.onOrderClick(order);
        });
        holder.btnViewDetail.setOnClickListener(v -> {
            listener.onOrderClick(order);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate, txtStatus, txtPhone, txtAddress, txtTotal;
        Button btnCancel, btnViewDetail;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnViewDetail = itemView.findViewById(R.id.btnViewDetail);
        }
    }
    public void updateData(List<Order> newOrders) {
        this.orderList.clear();
        this.orderList.addAll(newOrders);
        notifyDataSetChanged();
    }
}
