package com.example.myapplication.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class PhotoViewPager2Adapter extends RecyclerView.Adapter<PhotoViewPager2Adapter.PhotoViewHolder> {
    private List<Photo> mListPhoto;

    public PhotoViewPager2Adapter(List<Photo> mListPhoto) {
        this.mListPhoto = mListPhoto;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = mListPhoto.get(position);
        if (photo == null) {
            return;
        }
        holder.imgPhoto.setImageResource(photo.getResourceId());
    }

    @Override
    public int getItemCount() {
        return (mListPhoto != null) ? mListPhoto.size() : 0;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }
}

