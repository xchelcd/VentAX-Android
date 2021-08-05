package com.idax.ventax.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.R;

public class ProductViewHolder extends RecyclerView.ViewHolder{

    private ImageView imageProductImageView;
    private TextView titleProductTextView;
    private TextView priceProductTextView;
    private TextView descriptionProductTextView;
    private Switch stateProduct;

    private LinearLayout backgroundCardViewLinearLayout;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageProductImageView = itemView.findViewById(R.id.imageProductImageView);
        titleProductTextView = itemView.findViewById(R.id.titleProductTextView);
        descriptionProductTextView = itemView.findViewById(R.id.descriptionProductTextView);
        priceProductTextView = itemView.findViewById(R.id.priceProductTextView);
        backgroundCardViewLinearLayout = itemView.findViewById(R.id.backgroundCardViewLinearLayout);
        stateProduct = itemView.findViewById(R.id.stateProduct);
    }


    public ImageView getImageProductImageView() {
        return imageProductImageView;
    }

    public TextView getTitleProductTextView() {
        return titleProductTextView;
    }

    public TextView getPriceProductTextView() {
        return priceProductTextView;
    }

    public TextView getDescriptionProductTextView() {
        return descriptionProductTextView;
    }

    public LinearLayout getBackgroundCardViewLinearLayout() {
        return backgroundCardViewLinearLayout;
    }

    public Switch getStateProduct() {
        return stateProduct;
    }
}
