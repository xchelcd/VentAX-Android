package com.idax.ventax.ViewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idax.ventax.R;

public class CompanyViewHolder extends RecyclerView.ViewHolder {

    private ImageView logoImageView;
    private TextView nameTextView;
    private ImageButton favImageButton;

    public CompanyViewHolder(@NonNull View v) {
        super(v);
        logoImageView = v.findViewById(R.id.logoImageView);
        nameTextView = v.findViewById(R.id.nameTextView);
        favImageButton = v.findViewById(R.id.favImageButton);
    }

    public ImageView getLogoImageView() {
        return logoImageView;
    }

    public void setLogoImageView(ImageView logoImageView) {
        this.logoImageView = logoImageView;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public ImageButton getFavImageButton() {
        return favImageButton;
    }

    public void setFavImageButton(ImageButton favImageButton) {
        this.favImageButton = favImageButton;
    }
}
