package com.idax.ventax.Dialog.Order.Cancel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.idax.ventax.Activity.Order.OrderUPresenter;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.R;

import static com.idax.ventax.Extra.Constansts.CANCEL_TYPE_COMPANY;

public class CancelOrderDialog extends DialogFragment implements CancelOrderInterface {

    private Button noCancelButton;
    private Button cancelButton;

    private OrderModel orderModel;
    private int orderId;
    private OrderUPresenter presenter;
    private int type;
    private int id;
    private int position;

    public CancelOrderDialog(OrderModel orderModel, OrderUPresenter presenter, int type, int position) {
        this.presenter = presenter;
        this.type = type;
        this.orderModel = orderModel;
        orderId = orderModel.getOrderId();
        id = orderModel.getUserId();
        this.position = position;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cancel_order, null, false);
        builder.setView(view);
        inits(view);
        listeners();
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        return alertDialog;
    }

    private void listeners() {
        cancelButton.setOnClickListener(v -> {
            presenter.CancelOrder(orderModel, type, this, position);
        });

        noCancelButton.setOnClickListener(v -> {
            dismiss();
        });
    }

    private void inits(View v) {
        noCancelButton = v.findViewById(R.id.noCancelButton);
        cancelButton = v.findViewById(R.id.cancelButton);
    }

    @Override
    public void OnResponse(int response) {
        if (response == 1) dismiss();
    }
}
