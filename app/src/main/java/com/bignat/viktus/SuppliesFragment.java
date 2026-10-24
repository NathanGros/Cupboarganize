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
import java.util.List;

public class SuppliesFragment extends Fragment {
    private SupplyAdapter adapter;

    public SuppliesFragment() {
        super(R.layout.fragment_supplies);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView list = view.findViewById(R.id.supplies_list);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Supply> supplies = new ArrayList<>();
        supplies.add(new Supply("Egg", 6, Unit.PIECE, false));
        supplies.add(new Supply("Tomato", 4, Unit.PIECE, false));
        supplies.add(new Supply("Pasta", 1, Unit.KILOGRAM, false));

        adapter = new SupplyAdapter(supplies, SupplyAdapter.Mode.NONE);
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

        FloatingActionButton fab = view.findViewById(R.id.fab_add_supply);
        fab.setOnClickListener(v -> showAddSupplyDialog());
    }

    private void showAddSupplyDialog() {
        EditText input = new EditText(requireContext());
        input.setHint("Supply name");
        input.setSingleLine(true);

        new AlertDialog.Builder(requireContext())
                .setTitle("Add supply")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    double quantity = 1;
                    Unit unit = Unit.PIECE;
                    Supply supply = new Supply(name, quantity, unit, false);
                    if (!name.isEmpty()) {
                        adapter.addSupply(supply);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
