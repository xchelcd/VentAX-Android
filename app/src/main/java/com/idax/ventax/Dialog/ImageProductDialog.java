package com.idax.ventax.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idax.ventax.R;

public class ImageProductDialog extends DialogFragment {

    private ImageButton closeImageButton;
    private ImageView imageImageView;

    private Drawable drawable;
    private String url;

    public ImageProductDialog(Drawable drawable, String url) {
        this.drawable = drawable;
        this.url = url;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_image, null, false);
        inits(v);
        listeners();
        dialog.setView(v);
        AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        return alertDialog;
    }

    private void listeners() {
        closeImageButton.setOnClickListener(v->{
            dismiss();
        });
    }

    private void inits(View v) {
        closeImageButton = v.findViewById(R.id.closeImageButton);
        imageImageView = v.findViewById(R.id.imageImageView);

        imageImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        try {
            imageImageView.setImageDrawable(drawable);
        }catch (Exception e){
            Glide.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.progress_bar_image)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .error(0)
                    .into(imageImageView);
        }
    }
}
