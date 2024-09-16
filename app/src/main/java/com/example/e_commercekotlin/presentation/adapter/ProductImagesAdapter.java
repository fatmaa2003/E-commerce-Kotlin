package com.example.e_commercekotlin.presentation.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.data.model.ProductResponse;
import com.example.e_commercekotlin.data.model.ProductResponse.ProductResponseItem;

import java.util.ArrayList;
import java.util.List;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ComponentViewHolder> {

    private List<String> productImages = new ArrayList<>();


    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComponentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design, parent, false);
        return new ComponentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentViewHolder holder, int position) {
        String productImage = productImages.get(position);
        Glide.with(holder.imageView.getContext()).load(productImage).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productImages.size();
    }

    static class ComponentViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ComponentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

        }
    }
}
