package com.example.e_commercekotlin.presentation.screens;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.e_commercekotlin.R;
import com.example.e_commercekotlin.data.model.Brand;
import com.example.e_commercekotlin.presentation.adapter.BrandAdapter;
import java.util.ArrayList;
import java.util.List;

public class BrandFragment extends Fragment {
    private BrandAdapter brandAdapter;
    private List<Brand> brandList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brand, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        brandAdapter = new BrandAdapter();
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Bersheka", "12% OFF"));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Lacoste", ""));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Levi's", "12% OFF"));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Adidas", ""));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Zara", "12% OFF"));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Zara", "12% OFF"));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Zara", "12% OFF"));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Zara", "12% OFF"));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Zara", "12% OFF"));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Zara", "12% OFF"));
        brandList.add(new Brand(R.drawable.baseline_profile_24,"Zara", "12% OFF"));
        brandAdapter.setBrandList(brandList);
        RecyclerView recyclerView = view.findViewById(R.id.brand); // Ensure this ID matches
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(brandAdapter);

    }
}
