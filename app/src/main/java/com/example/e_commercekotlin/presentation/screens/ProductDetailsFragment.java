package com.example.e_commercekotlin.presentation.screens;

import static com.example.e_commercekotlin.Util.UtilKt.handleToolBarState;
import static com.google.android.gms.common.util.CollectionUtils.listOf;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.data.model.Product;
import com.example.e_commercekotlin.databinding.CustomToolbarBinding;
import com.example.e_commercekotlin.databinding.FragmentProductDetailsBinding;
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter;
import com.example.e_commercekotlin.presentation.model.Featured;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsFragment extends Fragment {
    private FragmentProductDetailsBinding binding;
    private ProductImagesAdapter productImagesAdapter;
    private List<ProductImage> productImages = new ArrayList<>();
    private List<Product>product=new ArrayList<>();
    private ProductAdapter productAdapter;
    private List<Featured>features=new ArrayList<>();
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
        CustomToolbarBinding toolbarBinding = binding.productDetailsFragmentToolbar;
        handleToolBarState(toolbarBinding,"",false,true,true,R.drawable.bookmark,R.drawable.down_arrow
        );

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
        productImagesAdapter = new ProductImagesAdapter();
        productImagesAdapter.setProductImages(productImages);
        productAdapter=new ProductAdapter();
        productAdapter.setProductList(product);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.clothes.setLayoutManager(layoutManager);
        binding.clothes.setAdapter(productImagesAdapter);
        binding.completeOutfit.setLayoutManager(layoutManager1);
        binding.completeOutfit.setAdapter(productAdapter);


        // Adding items to the list
        productImages.add(new ProductImage(R.drawable.baseline_profile_24));
        productImages.add(new ProductImage(R.drawable.baseline_profile_24));
        productImages.add(new ProductImage(R.drawable.baseline_profile_24));
        productImagesAdapter.setProductImages(productImages);
        product.add(new Product("T-shirt",200,listOf("https://i.imgur.com/Qphac99.jpeg")));
        product.add(new Product("T-shirt",200,listOf("https://i.imgur.com/Qphac99.jpeg")));
        product.add(new Product("T-shirt",200,listOf("https://i.imgur.com/Qphac99.jpeg")));
        product.add(new Product("T-shirt",200,listOf("https://i.imgur.com/Qphac99.jpeg")));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
