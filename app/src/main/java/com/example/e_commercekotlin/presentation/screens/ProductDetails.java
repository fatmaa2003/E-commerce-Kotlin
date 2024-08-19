package com.example.e_commercekotlin.presentation.screens;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.databinding.FragmentProductDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends Fragment {
    private FragmentProductDetailsBinding binding;
    private Adapter adapter;
    private List<ProductImage> productImages = new ArrayList<>();
    private boolean tagsVisible1 = false;
    private boolean tagsVisible2 = false;
    private boolean tagsVisible3 = false;
    private LinearLayout llTagsContent1;
    private TextView tvTagsHeader1;
    private LinearLayout llTagsContent2;
    private TextView tvTagsHeader2;
    private LinearLayout llTagsContent3;
    private TextView tvTagsHeader3;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Find Views
        llTagsContent1 = view.findViewById(R.id.ll_tags_content1);
        tvTagsHeader1 = view.findViewById(R.id.tv_tags_header1);
        llTagsContent2 = view.findViewById(R.id.ll_tags_content2);
        tvTagsHeader2 = view.findViewById(R.id.tv_tags_header2);
        llTagsContent3 = view.findViewById(R.id.ll_tags_content3);
        tvTagsHeader3 = view.findViewById(R.id.tv_tags_header3);

        // Set OnClickListener for the Headers
        tvTagsHeader1.setOnClickListener(v -> toggleTagsVisibility(llTagsContent1, tvTagsHeader1, 1));
        tvTagsHeader2.setOnClickListener(v -> toggleTagsVisibility(llTagsContent2, tvTagsHeader2, 2));
        tvTagsHeader3.setOnClickListener(v -> toggleTagsVisibility(llTagsContent3, tvTagsHeader3, 3));

        return view;
    }

    // Toggle function to show/hide the tags
    private void toggleTagsVisibility(LinearLayout tagsContent, TextView tagsHeader, int section) {
        boolean isVisible = false;
        if (section == 1) {
            isVisible = tagsVisible1;
            tagsVisible1 = !tagsVisible1;
        } else if (section == 2) {
            isVisible = tagsVisible2;
            tagsVisible2 = !tagsVisible2;
        } else if (section == 3) {
            isVisible = tagsVisible3;
            tagsVisible3 = !tagsVisible3;
        }

        if (isVisible) {
            tagsContent.setVisibility(View.GONE);
            tagsHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_expand_more_24, 0);
        } else {
            tagsContent.setVisibility(View.VISIBLE);
            tagsHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_expand_less_24, 0);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Adapter and RecyclerView
        adapter = new Adapter();
        adapter.setProductImages(productImages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.clothes.setLayoutManager(layoutManager);
        binding.clothes.setAdapter(adapter);

        // Adding items to the list
        productImages.add(new ProductImage(R.drawable.baseline_profile_24));
        productImages.add(new ProductImage(R.drawable.baseline_profile_24));
        productImages.add(new ProductImage(R.drawable.baseline_profile_24));
        adapter.setProductImages(productImages);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
