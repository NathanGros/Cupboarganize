package com.bignat.viktus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottom_nav);

        nav.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            if (id == R.id.nav_meals) {
                fragment = new MealsFragment();
            } else if (id == R.id.nav_supplies) {
                fragment = new SuppliesFragment();
            } else if (id == R.id.nav_shopping) {
                fragment = new ShoppingFragment();
            } else {
                fragment = new HistoryFragment();
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        });

        // Show Home on first launch
        if (savedInstanceState == null) {
            nav.setSelectedItemId(R.id.nav_meals);
        }
    }
}