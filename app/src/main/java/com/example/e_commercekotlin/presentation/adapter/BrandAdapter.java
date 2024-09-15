package com.example.e_commercekotlin.presentation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.data.model.Stores;
import com.example.e_commercekotlin.databinding.BranddesignBinding;
import com.example.e_commercekotlin.presentation.listener.StoreClickListener;

import java.util.List;
import java.util.Objects;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ProductViewHolder> {

    private List<Stores.StoresItem> brandList;

    private StoreClickListener storeClickListener;

    public void setStoreClickListener(StoreClickListener storeClickListener){
        this.storeClickListener = storeClickListener;
    }


    public void setBrandList(List<Stores.StoresItem> brandList) {
        if (brandList == null || brandList.isEmpty()) {
            return;
        }
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
        handleViewBinding(holder, brand);
    }

    private void handleViewBinding(@NonNull ProductViewHolder holder, Stores.StoresItem brand) {

        holder.binding.brandName.setText(brand.getName());

        if (brand.getDiscount() != null && brand.getDiscount() > 0) {
            String discountText = String.format("%.0f%% OFF", brand.getDiscount());
            holder.binding.discountPercentage.setText(discountText);
            holder.binding.discountPercentage.setVisibility(View.VISIBLE);
        } else {
            holder.binding.discountPercentage.setVisibility(View.INVISIBLE);
        }

        if (brand.getImageurl() != null && !brand.getImageurl().isEmpty()) {
            Glide.with(holder.binding.brandImage.image.getContext())
                    .load(brand.getImageurl())
                    .placeholder(R.drawable.img)
                    .into(holder.binding.brandImage.image);
        } else {
            holder.binding.brandImage.image.setImageResource(R.drawable.img);
        }

        holder.binding.getRoot().setOnClickListener(v -> storeClickListener.onStoreClicked(Objects.requireNonNull(brand.getStoreId()).toString()));
    }


    @Override
    public int getItemCount() {
        return brandList == null ? 0 : brandList.size();
    }
}
