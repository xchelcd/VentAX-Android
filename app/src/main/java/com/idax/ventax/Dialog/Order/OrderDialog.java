package com.idax.ventax.Dialog.Order;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.idax.ventax.Activity.Entrepreneur.EntrepreneurPresenter;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.OrderProduct;
import com.idax.ventax.Entity.OrderSQLite;
import com.idax.ventax.Entity.Product;
import com.idax.ventax.Entity.User;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;
import com.idax.ventax.RoomDB.AppDataBase.AppDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.RESULT_CODE_GO_TO_SHOPPING_CART;
import static com.idax.ventax.Extra.Util.customNotification;
import static com.idax.ventax.Extra.Util.formatDecimal;

public class OrderDialog extends DialogFragment implements OrderView {

    private OrderPresenter presenter;
    private User user;
    private Product product;
    private Company company;
    private String comment = "";
    private OrderModel orderModel;

    //private LoadingDialog dialog;

    public OrderDialog(User user, Product product, Company company) {
        presenter = new OrderPresenter(this);
        //dialog = new LoadingDialog(getActivity());
        this.user = user;
        this.product = product;
        this.company = company;
    }

    private TextView goToCartTextView;
    private TextView productTextView;
    private TextView finalPriceTextView;
    private TextView companyNameTextView;
    private TextView warningTextView;
    private EditText qtyEditText;
    private Button addToCartButton;
    private Button orderNowButton;
    private ProgressBar orderProgressBar;

    private int qty = 1;
    private double price;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_order, null, false);
        builder.setView(view);
        inits(view);
        setData();
        listeners();
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        return alertDialog;
    }

    private void setData() {
        productTextView.setText(product.getName());
        finalPriceTextView.setText(formatDecimal(product.getPrice() / 100));
        companyNameTextView.setText(company.getName());
        warningTextView.setText("Al aceptar usted esta consciente de que el dueño recibe su número para contactarlo en caso de algún problema");
    }

    private void listeners() {
        addToCartButton.setOnClickListener(v -> {
            if (qty == 0) {
                Toast.makeText(getContext(), getString(R.string.ingrese_qty_valid), Toast.LENGTH_SHORT).show();
                return;
            }
            OrderSQLite o = new OrderSQLite(0, company.getId(), company.getName(), product.getId(), product.getName(), qty, product.getPrice() / 100, "");
            OrderSQLite p = AppDataBase.getAppDataBase(getContext()).orderDao().getProduct(product.getId());
            if (p == null) AppDataBase.getAppDataBase(getContext()).orderDao().insertOrder(o);
            else
                AppDataBase.getAppDataBase(getContext()).orderDao().upadteQty(product.getId(), qty);
            dismiss();
        });
        orderNowButton.setOnClickListener(v -> {
            if (qty == 0) {
                Toast.makeText(getContext(), getString(R.string.ingrese_qty_valid), Toast.LENGTH_SHORT).show();
                return;
            }
            //List<OrderProduct> orderProductList = Collections.singletonList(new OrderProduct(product.getId(), qty));
            //orderProductList.add(new OrderProduct(product.getId(),qty));
            orderModel = new OrderModel(user.getId(),
                    company.getId(),
                    price,
                    Collections.singletonList(new OrderProduct(product.getId(), product.getName(), qty)),
                    comment);
            presenter.InsertOrder(orderModel);
        });
        goToCartTextView.setOnClickListener(v -> {
            getActivity().setResult(RESULT_CODE_GO_TO_SHOPPING_CART);
            getActivity().onBackPressed();
        });
        qtyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = "";
                try {
                    qty = Integer.parseInt(qtyEditText.getText().toString());
                    price = qty * product.getPrice() / 100;
                    str = String.valueOf(price);
                } catch (NumberFormatException nfe) {
                    str = "0";
                    qty = 0;
                    price = 0;
                    Toast.makeText(getContext(), getString(R.string.ingrese_qty_valid), Toast.LENGTH_SHORT).show();
                }
                finalPriceTextView.setText(formatDecimal(str));
            }
        });

        companyNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void inits(View v) {
        goToCartTextView = v.findViewById(R.id.goToCartTextView);
        productTextView = v.findViewById(R.id.productTextView);
        finalPriceTextView = v.findViewById(R.id.finalPriceTextView);
        companyNameTextView = v.findViewById(R.id.companyNameTextView);
        qtyEditText = v.findViewById(R.id.qtyEditText);
        addToCartButton = v.findViewById(R.id.addToCartButton);
        orderNowButton = v.findViewById(R.id.orderNowButton);
        orderProgressBar = v.findViewById(R.id.orderProgressBar);
        warningTextView = v.findViewById(R.id.warningTextView);
    }

    @Override
    public void onSuccessOrderNotification(String message) {
        customNotification(getContext(), message, orderModel, "channel4", 3, false);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onErrorOrderNotification(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(String message) {
        //dialog.openLoadingDialog(message);
        orderProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        //dialog.closeLoadingDialog();
        orderProgressBar.setVisibility(View.GONE);
    }
}
