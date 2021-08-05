package com.idax.ventax.Dialog.Order.MultiProducts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.idax.ventax.Entity.Order;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.OrderProduct;
import com.idax.ventax.Entity.OrderSQLite;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Fragment.ShoppingCart.CartPresenter;
import com.idax.ventax.Fragment.ShoppingCart.CartView;
import com.idax.ventax.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.idax.ventax.Extra.Util.customNotification;
import static com.idax.ventax.Extra.Util.formatDecimal;

public class ConfirmOrderDialog extends DialogFragment implements  ConfirmOrderView{

    private TextView totalTextView;
    private Button confirmButton;
    private Button cancelButton;

    private List<OrderSQLite> orderSQLiteList;
    private int userId;

    private double total;

    private OrderModel orderModel;

    private ConfirmOrderPresenter presenter;
    private CartView cartView;

    public ConfirmOrderDialog(CartView cartView, List<OrderSQLite> orderSQLiteList, int userId) {
        this.orderSQLiteList = orderSQLiteList;
        this.cartView = cartView;
        this.userId = userId;
        total = orderSQLiteList.stream().filter(Objects::nonNull).mapToDouble(x -> x.getQty() * x.getPrice()).reduce(Double::sum).orElse(0.0);
        orderModel = new OrderModel(userId, orderSQLiteList.get(0).getCompanyId(), total, getOrderProdcutList(orderSQLiteList), orderSQLiteList.get(0).getComment());
        //Gson gson = new Gson();
        //String json = gson.toJson(orderModel, OrderModel.class);
    }

    private List<OrderProduct> getOrderProdcutList(List<OrderSQLite> orderSQLiteList) {
        List<OrderProduct> list = new ArrayList<>();
        for (OrderSQLite op : orderSQLiteList) {
            list.add(new OrderProduct(op.getProductId(), op.getProductName(), op.getQty()));
        }
        return list;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm_order, null, false);
        builder.setView(view);
        inits(view);
        listeners();
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    private void listeners() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Procesando orden. Espere un momento", Toast.LENGTH_SHORT).show();
                presenter.InsertOrder(orderModel);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void inits(View v) {
        totalTextView = v.findViewById(R.id.totalTextView);
        confirmButton = v.findViewById(R.id.confirmButton);
        cancelButton = v.findViewById(R.id.cancelButton);
        totalTextView.setText(formatDecimal(total));

        presenter = new ConfirmOrderPresenter(this);
    }

    @Override
    public void OnSuccess() {
        customNotification(getContext(), "Pedido realizado.", orderModel, "channel4", 3, false);
        dismiss();
        cartView.OnOrderRequestSuccess(orderSQLiteList);
        Toast.makeText(getContext(), "Solicitud completada exitosamente.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnError() {
        Toast.makeText(getContext(), "Ocurrió un error.", Toast.LENGTH_SHORT).show();
    }
}
