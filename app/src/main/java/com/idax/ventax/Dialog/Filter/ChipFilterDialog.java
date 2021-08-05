package com.idax.ventax.Dialog.Filter;

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

import com.idax.ventax.R;

import java.util.ArrayList;
import java.util.List;

public class ChipFilterDialog extends DialogFragment {

    private List<Integer> categoriesId;

    private ChipFilterView view;
    public ChipFilterDialog(ChipFilterView view){
        //this.view = view;
    }

    private Button filterButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter_chip, null, false);
        builder.setView(v);
        inits(v);
        listeners();

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;
    }

    private void listeners() {
        //filterButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        //view.getAllCategoriesId(categoriesId);
        //    }
        //});
    }

    private void inits(View v) {
        categoriesId = new ArrayList<>(10);
    }
}
