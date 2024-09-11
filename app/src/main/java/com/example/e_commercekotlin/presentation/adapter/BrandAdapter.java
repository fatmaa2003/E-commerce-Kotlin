package com.example.e_commercekotlin.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercekotlin.data.model.Stores;
import com.example.e_commercekotlin.databinding.BranddesignBinding;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ProductViewHolder> {
    private Stores brandList;

    public void setBrandList(Stores brandList) {
        this.brandList = brandList;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final BranddesignBinding binding;

        public ProductViewHolder(BranddesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BranddesignBinding binding = BranddesignBinding.inflate(inflater, parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Stores.StoresItem brand = brandList.get(position);
        handleViewBiding(holder, brand);
    }

    private static void handleViewBiding(@NonNull ProductViewHolder holder, Stores.StoresItem brand) {
        holder.binding.brandImage.image.setImageResource(brand.getImageurl());
        holder.binding.brandName.setText(brand.getName());
//        if (brand.getOffer().isEmpty()){
//            holder.binding.discountPercentage.setVisibility(View.INVISIBLE);
//        } else {
//            holder.binding.discountPercentage.setVisibility(View.VISIBLE);
//        }
//        holder.binding.discountPercentage.setText(brand.getOffer());
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }
}
