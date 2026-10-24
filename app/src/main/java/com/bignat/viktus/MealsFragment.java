package com.bignat.viktus;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealsFragment extends Fragment {

    private MealAdapter adapter;

    public MealsFragment() {
        super(R.layout.fragment_meals);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView list = view.findViewById(R.id.meals_list);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<String> meals = new ArrayList<>(Arrays.asList(
                "Spaghetti bolognese", "Chicken salad", "Veggie stir-fry"));
        adapter = new MealAdapter(meals);
        list.setAdapter(adapter);

        // Long press + drag to reorder
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView rv,
                                          @NonNull RecyclerView.ViewHolder from,
                                          @NonNull RecyclerView.ViewHolder to) {
                        adapter.moveItem(from.getAdapterPosition(),
                                to.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder vh, int direction) {
                        // swipe disabled
                    }
                });
        helper.attachToRecyclerView(list);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_meal);
        fab.setOnClickListener(v -> showAddMealDialog());
    }

    private void showAddMealDialog() {
        EditText input = new EditText(requireContext());
        input.setHint("Meal name");
        input.setSingleLine(true);

        new AlertDialog.Builder(requireContext())
                .setTitle("Add meal")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty()) {
                        adapter.addMeal(name);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}