package com.example.e_commercekotlin.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.data.model.Brand;
import com.example.e_commercekotlin.databinding.BranddesignBinding;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ProductViewHolder> {
    private List<Brand> brandList;

    public void setBrandList(List<Brand> brandList) {
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
        Brand brand = brandList.get(position);
        handleViewBiding(holder, brand);
    }

    private static void handleViewBiding(@NonNull ProductViewHolder holder, Brand brand) {
//        holder.binding.brandImage.image.setImageResource(brand.getImageBrand());
        holder.binding.brandName.setText(brand.getName());
        if (brand.getOffer().isEmpty()){
            holder.binding.discountPercentage.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.discountPercentage.setVisibility(View.VISIBLE);
        }
        holder.binding.discountPercentage.setText(brand.getOffer());
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }
}
