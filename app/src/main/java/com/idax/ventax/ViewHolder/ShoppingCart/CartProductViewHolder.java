package com.idax.ventax.ViewHolder.ShoppingCart;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.R;

public class CartProductViewHolder extends RecyclerView.ViewHolder {

    private TextView productNameTextView;
    private TextView priceTextView;
    private ImageView deleteProductImageButton;
    private ImageView editProductImageButton;

    private EditText qtyEditText;
    public CartProductViewHolder(@NonNull View v) {
        super(v);
        productNameTextView = v.findViewById(R.id.productNameTextView);
        priceTextView = v.findViewById(R.id.priceTextView);
        deleteProductImageButton = v.findViewById(R.id.deleteProductImageButton);
        editProductImageButton = v.findViewById(R.id.editProductImageButton);
        qtyEditText = v.findViewById(R.id.qtyEditText);
    }

    public EditText getQtyEditText() {
        return qtyEditText;
    }

    public void setQtyEditText(EditText qtyEditText) {
        this.qtyEditText = qtyEditText;
    }

    public TextView getProductNameTextView() {
        return productNameTextView;
    }

    public void setProductNameTextView(TextView productNameTextView) {
        this.productNameTextView = productNameTextView;
    }

    public TextView getPriceTextView() {
        return priceTextView;
    }

    public void setPriceTextView(TextView priceTextView) {
        this.priceTextView = priceTextView;
    }

    public ImageView getDeleteProductImageButton() {
        return deleteProductImageButton;
    }

    public void setDeleteProductImageButton(ImageButton deleteProductImageButton) {
        this.deleteProductImageButton = deleteProductImageButton;
    }

    public ImageView getEditProductImageButton() {
        return editProductImageButton;
    }

    public void setEditProductImageButton(ImageButton editProductImageButton) {
        this.editProductImageButton = editProductImageButton;
    }
}
