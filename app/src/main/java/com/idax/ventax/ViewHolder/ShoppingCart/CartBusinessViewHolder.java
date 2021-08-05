package com.idax.ventax.ViewHolder.ShoppingCart;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.R;

public class CartBusinessViewHolder extends RecyclerView.ViewHolder {

    private TextView businessNameTextView;
    private Button getAllProductsButton;
    private RecyclerView recyclerView;
    public CartBusinessViewHolder(@NonNull View v) {
        super(v);
        businessNameTextView = v.findViewById(R.id.businessNameTextView);
        getAllProductsButton = v.findViewById(R.id.getAllProductsButton);
        recyclerView = v.findViewById(R.id.ordersRecyclereView);
    }

    public TextView getBusinessNameTextView() {
        return businessNameTextView;
    }

    public void setBusinessNameTextView(TextView businessNameTextView) {
        this.businessNameTextView = businessNameTextView;
    }

    public Button getGetAllProductsButton() {
        return getAllProductsButton;
    }

    public void setGetAllProductsButton(Button getAllProductsButton) {
        this.getAllProductsButton = getAllProductsButton;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
