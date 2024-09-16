package com.example.e_commercekotlin.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.data.model.AddToCartRequest;
import com.example.e_commercekotlin.data.model.CardModel;
import com.example.e_commercekotlin.databinding.PaymentCardItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder>  {

    List<CardModel> cardModels = new ArrayList<>();

    public void setCardModels(List<CardModel> cardModels) {
        this.cardModels = cardModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaymentCardItemBinding binding = PaymentCardItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardModel card = cardModels.get(position);
        holder.binding.cardNumber.setText(card.getCardNumber());
        handleCardType(holder, card);

    }

    private static void handleCardType(@NonNull ViewHolder holder, CardModel card) {
        switch (card.getType()){
            case MASTER_CARD: {
                    holder.binding.getRoot().setCardBackgroundColor(holder.binding.getRoot().getContext().getColor(R.color.master_card_card_view_bg));
                    holder.binding.cardTypeText.setText("Mastercard");
                    holder.binding.cardImage.setImageResource(R.drawable.mastercardfinal);

                break;

            }
            case VISA: {
                // fatma eli msmya el color dh ya gd3an
                holder.binding.getRoot().setCardBackgroundColor(holder.binding.getRoot().getContext().getColor(R.color.lightgray2));
                holder.binding.cardTypeText.setText("Visa");
                holder.binding.cardImage.setImageResource(R.drawable.visa_svgrepo_com);
                break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return cardModels.size();
    }

    @NotNull
    public List<AddToCartRequest.Product> getProductItems() {
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        PaymentCardItemBinding binding;
        public ViewHolder(@NonNull PaymentCardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
