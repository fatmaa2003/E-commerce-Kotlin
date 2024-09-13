package com.example.e_commercekotlin.presentation.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.data.model.Stores;
import com.example.e_commercekotlin.databinding.BranddesignBinding;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ProductViewHolder> {

    private List<Stores.StoresItem> brandList;
    private final int[] drawableResources = {
            R.drawable.lacoste,
            R.drawable.breshkaa,
            R.drawable.adidas,
            R.drawable.zara,
            R.drawable.nike,
            R.drawable.gucci,
            R.drawable.prada,
            R.drawable.boss,
            R.drawable.leviss


    };
    private Context context;


    public BrandAdapter(Context context) {
        this.context = context;
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
        handleViewBinding(holder, brand, position);
    }

    private void handleViewBinding(@NonNull ProductViewHolder holder, Stores.StoresItem brand, int position) {

        holder.binding.brandName.setText(brand.getName());


        Drawable drawable = getDrawableFromResources(position % drawableResources.length);
        holder.binding.brandImage.image.setImageDrawable(drawable);
    }

    private Drawable getDrawableFromResources(int index) {
        return ContextCompat.getDrawable(context, drawableResources[index]);
    }

    @Override
    public int getItemCount() {
        return brandList == null ? 0 : brandList.size();
    }
}
