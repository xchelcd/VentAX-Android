package com.idax.ventax.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    private TableLayout orderItemsTableLayout;
    private TextView orderIdTextView;
    private TextView companyNameTextView;
    private TextView dateTextView;
    private TextView stateOrderTextView;
    private TextView totalOrderTextView;
    private Button cancelOrderUButton;

    public OrderViewHolder(@NonNull View v) {
        super(v);
        orderItemsTableLayout = v.findViewById(R.id.orderItemsTableLayout);
        orderIdTextView = v.findViewById(R.id.orderIdTextView);
        companyNameTextView = v.findViewById(R.id.companyNameTextView);
        dateTextView = v.findViewById(R.id.dateTextView);
        stateOrderTextView = v.findViewById(R.id.stateOrderTextView);
        totalOrderTextView = v.findViewById(R.id.totalOrderTextView);
        cancelOrderUButton = v.findViewById(R.id.cancelOrderUButton);
    }

    public Button getCancelOrderUButton() {
        return cancelOrderUButton;
    }

    public void setCancelOrderUButton(Button cancelOrderUButton) {
        this.cancelOrderUButton = cancelOrderUButton;
    }

    public TableLayout getOrderItemsTableLayout() {
        return orderItemsTableLayout;
    }

    public void setOrderItemsTableLayout(TableLayout orderItemsTableLayout) {
        this.orderItemsTableLayout = orderItemsTableLayout;
    }

    public TextView getOrderIdTextView() {
        return orderIdTextView;
    }

    public void setOrderIdTextView(TextView orderIdTextView) {
        this.orderIdTextView = orderIdTextView;
    }

    public TextView getCompanyNameTextView() {
        return companyNameTextView;
    }

    public void setCompanyNameTextView(TextView companyNameTextView) {
        this.companyNameTextView = companyNameTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public void setDateTextView(TextView dateTextView) {
        this.dateTextView = dateTextView;
    }

    public TextView getStateOrderTextView() {
        return stateOrderTextView;
    }

    public void setStateOrderTextView(TextView stateOrderTextView) {
        this.stateOrderTextView = stateOrderTextView;
    }

    public TextView getTotalOrderTextView() {
        return totalOrderTextView;
    }

    public void setTotalOrderTextView(TextView totalOrderTextView) {
        this.totalOrderTextView = totalOrderTextView;
    }
}
