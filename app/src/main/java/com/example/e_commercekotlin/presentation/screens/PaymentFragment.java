package com.example.e_commercekotlin.presentation.screens;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.Util.ShippingAddress;
import com.example.e_commercekotlin.Util.UtilJava;
import com.example.e_commercekotlin.data.ProfileUserDetails;
import com.example.e_commercekotlin.data.model.CardModel;
import com.example.e_commercekotlin.data.model.Followings;
import com.example.e_commercekotlin.databinding.FollowingButtonBinding;
import com.example.e_commercekotlin.databinding.FragmentPaymentBinding;
import com.example.e_commercekotlin.presentation.adapter.AddressAdapter;
import com.example.e_commercekotlin.presentation.adapter.PaymentAdapter;
import com.example.e_commercekotlin.presentation.adapter.ProfileBottomSheetAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {
    private PaymentAdapter paymentAdapter;
    private List<CardModel> cardModels= new ArrayList<>();
    private FragmentPaymentBinding binding;
    private AddressAdapter adapter;
    private ProfileBottomSheetAdapter profileBottomSheetAdapter;
    private List<Followings> followings=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paymentAdapter = new PaymentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.cardRecyclerView.setLayoutManager(layoutManager);
        adapter = new AddressAdapter();
        adapter.setAddressList(ShippingAddress.INSTANCE.getAddresses());
        adapter.setIsProfileAdapter(true);
        binding.recyclerViewAddresses.setAdapter(adapter);
        binding.cardRecyclerView.setAdapter(paymentAdapter);
        cardModels.add(new CardModel(CardModel.CardType.MASTER_CARD, "123988"));
        cardModels.add(new CardModel(CardModel.CardType.VISA, "18972"));
        cardModels.add(new CardModel(CardModel.CardType.VISA, "18972"));

        paymentAdapter.setCardModels(cardModels);
        binding.profileFollowingButton.followingButtonTv.setPadding(UtilJava.dpToPx(20), UtilJava.dpToPx(10),UtilJava.dpToPx(20), UtilJava.dpToPx(10));
        binding.profileFollowingButton.getRoot().setOnClickListener(v -> showBottomSheet());

        binding.profileName.setText(ProfileUserDetails.INSTANCE.getFirstName()+ " " + ProfileUserDetails.INSTANCE.getLastName());
//        Log.d("in payment fragment ",ProfileUserDetails.INSTANCE.getFirstName());
        binding.profileUsername.setText(ProfileUserDetails.INSTANCE.getUsername());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showBottomSheet() {
        View dialogView = getLayoutInflater().inflate(R.layout.profile_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        dialog.setContentView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.profile_bottom_sheet_recyclerview);
        profileBottomSheetAdapter = new ProfileBottomSheetAdapter();
        recyclerView.setAdapter(profileBottomSheetAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        profileBottomSheetAdapter.setFollowingList(followings);

        dialog.show();
    }

}