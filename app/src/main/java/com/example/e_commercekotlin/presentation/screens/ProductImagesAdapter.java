package com.example.e_commercekotlin.presentation.screens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercekotlin.R;

import java.util.ArrayList;
import java.util.List;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ComponentViewHolder> {

    private List<ProductImage> productImages = new ArrayList<>();
//    private FragmentProductDetailsBinding binding;

public void setProductImages(List<ProductImage> productImages) {
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
        ProductImage productImage = productImages.get(position);
        holder.imageView.setImageResource(productImage.getImage());
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
//           .image_view.setImageResource(R.drawable.baseline_profile_24);

        }
    }
}
