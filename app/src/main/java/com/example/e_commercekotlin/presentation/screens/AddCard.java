package com.example.e_commercekotlin.presentation.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commercekotlin.data.model.CardModel;
import com.example.e_commercekotlin.databinding.FragmentAddToCartBinding;
import com.example.e_commercekotlin.presentation.adapter.PaymentAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddCard extends Fragment {
    private PaymentAdapter paymentAdapter;
    private List<CardModel> cardModels= new ArrayList<>();
    private FragmentAddToCartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddToCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paymentAdapter = new PaymentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.addToCartRecyclerview.setLayoutManager(layoutManager);
        binding.addToCartRecyclerview.setAdapter(paymentAdapter);
        cardModels.add(new CardModel(CardModel.CardType.MASTER_CARD, "123988"));
        cardModels.add(new CardModel(CardModel.CardType.VISA, "18972"));
        cardModels.add(new CardModel(CardModel.CardType.VISA, "12398"));
        cardModels.add(new CardModel(CardModel.CardType.VISA, "18972"));
        cardModels.add(new CardModel(CardModel.CardType.VISA, "18972"));
        cardModels.add(new CardModel(CardModel.CardType.VISA, "18972"));
        paymentAdapter.setCardModels(cardModels);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}