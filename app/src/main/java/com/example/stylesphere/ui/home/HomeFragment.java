package com.example.stylesphere.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stylesphere.DressesActivity;
import com.example.stylesphere.LongSleeveActivity;
import com.example.stylesphere.PantsActivity;
import com.example.stylesphere.R;
import com.example.stylesphere.ShortSleeveActivity;
import com.example.stylesphere.ShortsActivity;
import com.example.stylesphere.SkirtsActivity;
import com.example.stylesphere.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button shortSleeveButton;
    private Button longSleeveButton;
    private Button pantsButton;
    private Button shortsButton;
    private Button dressesButton;
    private Button skirtsButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        shortSleeveButton = root.findViewById(R.id.shortSleeveTops);
        longSleeveButton = root.findViewById(R.id.longSleeveTops);
        pantsButton = root.findViewById(R.id.pants);
        shortsButton = root.findViewById(R.id.shorts);
        skirtsButton = root.findViewById(R.id.skirts);
        dressesButton = root.findViewById(R.id.dresses);

        shortSleeveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShortSleevePage();
            }
        });

        longSleeveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLongSleevePage();
            }
        });

        pantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPantsPage();
            }
        });

        shortsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShortsPage();
            }
        });

        skirtsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSkirtsPage();
            }
        });

        dressesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDressesPage();
            }
        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void openShortSleevePage() {
        Intent intent = new Intent(requireContext(), ShortSleeveActivity.class);
        startActivity(intent);
    }

    public void openLongSleevePage() {
        Intent intent = new Intent(requireContext(), LongSleeveActivity.class);
        startActivity(intent);
    }

    public void openPantsPage() {
        Intent intent = new Intent(requireContext(), PantsActivity.class);
        startActivity(intent);
    }

    public void openShortsPage() {
        Intent intent = new Intent(requireContext(), ShortsActivity.class);
        startActivity(intent);
    }

    public void openSkirtsPage() {
        Intent intent = new Intent(requireContext(), SkirtsActivity.class);
        startActivity(intent);
    }

    public void openDressesPage() {
        Intent intent = new Intent(requireContext(), DressesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}