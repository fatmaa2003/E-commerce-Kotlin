package com.example.e_commercekotlin.presentation.adapter;


import android.util.Log;
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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

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
        handleRoundedImageRadius(position,holder);
        Glide.with(holder.imageView.getContext()).load(productImage).into(holder.imageView);
    }

    private void handleRoundedImageRadius(int position, ComponentViewHolder holder) {
        ShapeAppearanceModel shapeAppearanceModel;

        if (position == 0) {
            shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 50f)
                    .setBottomLeftCorner(CornerFamily.ROUNDED, 50f)
                    .setTopRightCorner(CornerFamily.ROUNDED, 0f)
                    .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                    .build();
        } else if (position == productImages.size() - 1) {
            shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 0f)
                    .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                    .setTopRightCorner(CornerFamily.ROUNDED, 50f)
                    .setBottomRightCorner(CornerFamily.ROUNDED, 50f)
                    .build();
        } else {
            shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(CornerFamily.ROUNDED, 0f)
                    .build();
        }

        holder.imageView.setShapeAppearanceModel(shapeAppearanceModel);
    }


    @Override
    public int getItemCount() {
        return productImages.size();
    }

    static class ComponentViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageView;

        public ComponentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

        }
    }
}
