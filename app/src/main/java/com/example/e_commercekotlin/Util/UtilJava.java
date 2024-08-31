package com.example.e_commercekotlin.Util;

import static android.provider.Settings.System.getString;

import android.content.res.Resources;
import android.view.View;

import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.databinding.CustomToolbarBinding;

public class UtilJava {
    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public void handleToolBarState(
            CustomToolbarBinding binding,
            String toolBarTitle,
            boolean searchVisibility,
            boolean rightIconVisibility,
            boolean leftIconVisibility,
            int rightIconImage,
            int leftIconImage,
            int searchiconImage
    ) {
        binding.toolbarTitle.setText(toolBarTitle);
        binding.searchIcon.setVisibility(searchVisibility ? View.VISIBLE : View.GONE);
        binding.placeHolderIcon.setVisibility(rightIconVisibility ? View.VISIBLE : View.GONE);
        binding.leftIcon.setVisibility(leftIconVisibility ? View.VISIBLE : View.GONE);
        binding.placeHolderIcon.setImageResource(rightIconImage);
        binding.leftIcon.setImageResource(leftIconImage);
        binding.searchIcon.setImageResource(searchiconImage);
    }

    // If you want to use default values similar to Kotlin:
    public void handleToolBarState(CustomToolbarBinding binding) {
        handleToolBarState(binding, "", true, false, true, R.drawable.disk, R.drawable.back,R.drawable.search_icon);
    }

}
