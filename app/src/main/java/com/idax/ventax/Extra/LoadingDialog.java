package com.idax.ventax.Extra;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.idax.ventax.R;

import java.util.Objects;

public class LoadingDialog {

    private Context context;
    private AlertDialog alertDialog;

    private TextView textProgressBarTextView;
    private View v;

    public LoadingDialog(Context context) {
        this.context = context;
        inits();
    }

    @SuppressLint("InflateParams")
    private void inits() {
        v = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null, false);
        textProgressBarTextView = v.findViewById(R.id.textProgressBarTextView);
    }

    public void openLoadingDialog(String text) {
        try {
            if(v.getParent() == null) {
                textProgressBarTextView.setText(text);
                AlertDialog.Builder builder = new AlertDialog
                        .Builder(context)
                        .setView(v)
                        .setCancelable(false);
                alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeLoadingDialog() {
        alertDialog.dismiss();
    }
}
