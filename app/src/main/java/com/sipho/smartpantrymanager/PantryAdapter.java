package com.sipho.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private ArrayList<PantryItem> pantryItems;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    public PantryAdapter(
            ArrayList<PantryItem> pantryItems,
            OnItemActionListener listener) {

        this.pantryItems = pantryItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        PantryItem item = pantryItems.get(position);

        holder.itemName.setText("Item: " + item.getItemName());

        holder.quantity.setText(
                "Quantity: " + item.getQuantity()
        );

        holder.expiryDate.setText(
                "Expiry Date: " + item.getExpiryDate()
        );

        holder.editButton.setOnClickListener(v -> {
            listener.onEdit(item);
        });

        holder.deleteButton.setOnClickListener(v -> {
            listener.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView quantity;
        TextView expiryDate;

        Button editButton;
        Button deleteButton;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.itemQuantity);
            expiryDate = itemView.findViewById(R.id.itemExpiry);

            editButton = itemView.findViewById(R.id.btnEdit);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }
    }
}