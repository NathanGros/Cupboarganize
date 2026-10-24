package com.bignat.viktus;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SupplyAdapter extends RecyclerView.Adapter<SupplyAdapter.ViewHolder> {

    public enum Mode { NONE, STATUS, CHECKBOX }

    private final List<Supply> supplies;
    private final Mode mode;

    public SupplyAdapter(List<Supply> supplies, Mode mode) {
        this.supplies = supplies;
        this.mode = mode;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView status;
        final CheckBox checkBox;
        final TextView name;
        final TextView quantity;

        ViewHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.supply_status);
            checkBox = itemView.findViewById(R.id.supply_checkbox);
            name = itemView.findViewById(R.id.supply_name);
            quantity = itemView.findViewById(R.id.supply_quantity);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_supply, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Supply s = supplies.get(position);

        holder.name.setText(s.name);
        holder.quantity.setText(formatQuantity(s));

        holder.status.setVisibility(mode == Mode.STATUS ? View.VISIBLE : View.GONE);
        holder.checkBox.setVisibility(mode == Mode.CHECKBOX ? View.VISIBLE : View.GONE);

        if (mode == Mode.STATUS) {
            holder.status.setImageResource(s.checked ? R.drawable.ic_check : R.drawable.ic_close);
            holder.status.setColorFilter(s.checked ? Color.parseColor("#2E7D32")
                    : Color.parseColor("#C62828"));
        }

        if (mode == Mode.CHECKBOX) {
            // Clear the old listener first, rows are recycled and would otherwise fire it
            holder.checkBox.setOnCheckedChangeListener(null);
            holder.checkBox.setChecked(s.checked);
            holder.checkBox.setOnCheckedChangeListener((btn, isChecked) -> s.checked = isChecked);
        }
    }

    @Override
    public int getItemCount() {
        return supplies.size();
    }

    public void moveItem(int from, int to) {
        Supply supply = supplies.remove(from);
        supplies.add(to, supply);
        notifyItemMoved(from, to);
    }

    private String formatQuantity(Supply s) {
        String number = (s.quantity == Math.floor(s.quantity))
                ? String.valueOf((long) s.quantity)   // 2.0 -> "2"
                : String.valueOf(s.quantity);         // 0.5 -> "0.5"
        return number + " " + s.unit.label;
    }

    public void addSupply(Supply supply) {
        supplies.add(supply);
        notifyItemInserted(supplies.size() - 1);
    }
}