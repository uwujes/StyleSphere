package com.example.stylesphere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.stylesphere.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button shortSleeveButton;
    private Button longSleeveButton;
    private Button pantsButton;
    private Button shortsButton;
    private Button dressesButton;
    private Button skirtsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_closet, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        shortSleeveButton = (Button) findViewById(R.id.shortSleeveTops);
        longSleeveButton = (Button) findViewById(R.id.longSleeveTops);
        pantsButton = (Button) findViewById(R.id.pants);
        shortsButton = (Button) findViewById(R.id.shorts);
        skirtsButton = (Button) findViewById(R.id.skirts);
        dressesButton = (Button) findViewById(R.id.dresses);

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
    }

    public void openShortSleevePage() {
        Intent intent = new Intent(this, ShortSleeveActivity.class);
        startActivity(intent);
    }

    public void openLongSleevePage() {
        Intent intent = new Intent(this, LongSleeveActivity.class);
        startActivity(intent);
    }

    public void openPantsPage() {
        Intent intent = new Intent(this, PantsActivity.class);
        startActivity(intent);
    }

    public void openShortsPage() {
        Intent intent = new Intent(this, ShortsActivity.class);
        startActivity(intent);
    }

    public void openSkirtsPage() {
        Intent intent = new Intent(this, SkirtsActivity.class);
        startActivity(intent);
    }

    public void openDressesPage() {
        Intent intent = new Intent(this, DressesActivity.class);
        startActivity(intent);
    }
}