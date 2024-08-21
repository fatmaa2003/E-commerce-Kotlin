package com.example.e_commercekotlin.presentation.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.databinding.FragmentFollowBinding;
import com.example.e_commercekotlin.presentation.adapter.FollowingAdapter;
import com.example.e_commercekotlin.presentation.model.Featured;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FollowFragment extends Fragment {

    private FragmentFollowBinding bindingFollow;
    private FollowingAdapter followingAdapter;
    private List<Featured> features = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        bindingFollow = FragmentFollowBinding.inflate(inflater, container, false);
        View view = bindingFollow.getRoot();
        bindingFollow.followButton.buttonTv.setText("Follow");
        bindingFollow.followButton.buttonTv.setTextColor(ContextCompat.getColor(bindingFollow.followButton.getRoot().getContext(), R.color.white));


        bindingFollow.followButton.getRoot().setOnClickListener(new View.OnClickListener() {
            private boolean isFollowing = false;

            @Override
            public void onClick(View v) {
                if (isFollowing) {
                    bindingFollow.followButton.buttonTv.setText("Follow");
                    bindingFollow.followButton.getRoot().setBackgroundResource(R.drawable.follow);
                    bindingFollow.followButton.buttonTv.setTextColor(ContextCompat.getColor(bindingFollow.followButton.getRoot().getContext(), R.color.white));
                    Snackbar.make(view, "Sustainable removed from your feed tabs", Snackbar.LENGTH_SHORT).show();
                } else {
                    bindingFollow.followButton.buttonTv.setText("Following");
                    bindingFollow.followButton.buttonTv.setTextColor(ContextCompat.getColor(bindingFollow.followButton.getRoot().getContext(), R.color.black));
                    bindingFollow.followButton.getRoot().setBackgroundResource(R.drawable.following);  // Change to the grey drawable background
                    Snackbar.make(view, "Sustainable added to your feed tabs", Snackbar.LENGTH_SHORT).show();
                }
                isFollowing = !isFollowing;
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Collections"));
        tabLayout.addTab(tabLayout.newTab().setText("Stores"));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        followingAdapter = new FollowingAdapter();
        bindingFollow.followrecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
        bindingFollow.followrecycle.setAdapter(followingAdapter);

        features.add(new Featured(R.drawable.baseline_profile_24, "T-shirt", "too tight", "200"));
        features.add(new Featured(R.drawable.baseline_profile_24, "T-shirt", "too tight", "200"));
        features.add(new Featured(R.drawable.baseline_profile_24, "T-shirt", "too tight", "200"));
        features.add(new Featured(R.drawable.baseline_profile_24, "T-shirt", "too tight", "200"));
        features.add(new Featured(R.drawable.baseline_profile_24, "T-shirt", "too tight", "200"));
        features.add(new Featured(R.drawable.baseline_profile_24, "T-shirt", "too tight", "200"));


        followingAdapter.setFeatures(features);
    }
}
