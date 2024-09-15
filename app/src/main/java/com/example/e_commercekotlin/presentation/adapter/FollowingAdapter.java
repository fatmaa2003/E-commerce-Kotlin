package com.example.e_commercekotlin.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.presentation.model.Featured;

import java.util.ArrayList;
import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ComponentViewHolder> {

    private List<Featured> features = new ArrayList<>();

    public void setFeatures(List<Featured> features) {
        this.features = features;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComponentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ComponentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentViewHolder holder, int position) {
        Featured featured = features.get(position);

        // Set image, title, and price
        holder.imageView.setImageResource(featured.getImageResId());
        holder.textView1.setText(featured.getTitle());
        holder.textView3.setText(String.valueOf(featured.getPrice())); // Ensure price is a String
    }

    @Override
    public int getItemCount() {
        return features.size();
    }

    static class ComponentViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView3;

        public ComponentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            textView1 = itemView.findViewById(R.id.product_name);
            textView3 = itemView.findViewById(R.id.product_price);
        }
    }
}
