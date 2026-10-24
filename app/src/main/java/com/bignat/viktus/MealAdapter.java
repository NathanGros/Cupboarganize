package com.bignat.viktus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private final List<String> meals;

    public MealAdapter(List<String> meals) {
        this.meals = meals;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.meal_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void moveItem(int from, int to) {
        String meal = meals.remove(from);
        meals.add(to, meal);
        notifyItemMoved(from, to);
    }

    public void addMeal(String name) {
        meals.add(name);
        notifyItemInserted(meals.size() - 1);
    }
}