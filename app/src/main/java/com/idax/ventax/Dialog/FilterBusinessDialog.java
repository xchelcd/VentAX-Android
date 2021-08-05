package com.idax.ventax.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.idax.ventax.Dialog.Filter.ChipFilterView;
import com.idax.ventax.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterBusinessDialog extends DialogFragment {

    private ChipFilterView view;

    private CheckBox allCheckBox;
    private CheckBox foodCheckBox;
    private CheckBox electronicCheckBox;
    private CheckBox healthCheckBox;
    private CheckBox styleCheckBox;
    private CheckBox dessertCheckBox;
    private CheckBox sportCheckBox;
    private CheckBox fashionCheckBox;
    private CheckBox craftsCheckBox;
    private CheckBox giftsCheckBox;
    private CheckBox petsCheckBox;
    private CheckBox clothesCheckBox;
    private CheckBox accesoryCheckBox;
    private CheckBox artCheckBox;

    private Button filterButton;
    private ImageView cancelImageView;

    private List<CheckBox> checkBoxList;

    private List<Integer> categoriesId;

    private View v;

    private boolean flag = true;

    public FilterBusinessDialog(ChipFilterView view, List<Integer> categoriesId) {
        this.categoriesId = categoriesId;
        this.view = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter_business, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(v);
        inits(v);
        listeners();
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private void listeners() {
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getAllCategoriesId(categoriesId);
                dismiss();
            }
        });
        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (flag) {
                    checkBoxList.forEach(x -> x.setChecked(isChecked));
                    if (!isChecked)
                        categoriesId = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
                }
                if (isChecked)
                    categoriesId = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
                flag = true;
            }
        });
        foodCheckBox.setOnClickListener(onClickListener);
        electronicCheckBox.setOnClickListener(onClickListener);
        healthCheckBox.setOnClickListener(onClickListener);
        styleCheckBox.setOnClickListener(onClickListener);
        dessertCheckBox.setOnClickListener(onClickListener);
        sportCheckBox.setOnClickListener(onClickListener);
        fashionCheckBox.setOnClickListener(onClickListener);
        craftsCheckBox.setOnClickListener(onClickListener);
        giftsCheckBox.setOnClickListener(onClickListener);
        petsCheckBox.setOnClickListener(onClickListener);
        clothesCheckBox.setOnClickListener(onClickListener);
        accesoryCheckBox.setOnClickListener(onClickListener);
        artCheckBox.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.foodCheckBox:
                case R.id.electronicCheckBox:
                case R.id.healthCheckBox:
                case R.id.styleCheckBox:
                case R.id.dessertCheckBox:
                case R.id.sportCheckBox:
                case R.id.fashionCheckBox:
                case R.id.craftsCheckBox:
                case R.id.giftsCheckBox:
                case R.id.petsCheckBox:
                case R.id.clothesCheckBox:
                case R.id.accesoryCheckBox:
                case R.id.artCheckBox:
                    flag = false;
                    change(v);
                    break;
            }

        }
    };

    private void change(View v) {
        CheckBox c = this.v.findViewById(v.getId());
        int id = Integer.parseInt(c.getTag().toString());
        categoriesId.set(id, (c.isChecked()) ? id : -1);
        allCheckBox.setChecked(checkBoxList.stream().allMatch(CompoundButton::isChecked));
        flag = true;
    }

    private void inits(View v) {
        filterButton = v.findViewById(R.id.filterButton);
        cancelImageView = v.findViewById(R.id.cancelImageView);

        allCheckBox = v.findViewById(R.id.allCheckBox);
        foodCheckBox = v.findViewById(R.id.foodCheckBox);
        electronicCheckBox = v.findViewById(R.id.electronicCheckBox);
        healthCheckBox = v.findViewById(R.id.healthCheckBox);
        styleCheckBox = v.findViewById(R.id.styleCheckBox);
        dessertCheckBox = v.findViewById(R.id.dessertCheckBox);
        sportCheckBox = v.findViewById(R.id.sportCheckBox);
        fashionCheckBox = v.findViewById(R.id.fashionCheckBox);
        craftsCheckBox = v.findViewById(R.id.craftsCheckBox);
        giftsCheckBox = v.findViewById(R.id.giftsCheckBox);
        petsCheckBox = v.findViewById(R.id.petsCheckBox);
        clothesCheckBox = v.findViewById(R.id.clothesCheckBox);
        accesoryCheckBox = v.findViewById(R.id.accesoryCheckBox);
        artCheckBox = v.findViewById(R.id.artCheckBox);

        checkBoxList = new ArrayList<>();
        fillCheckBoxList();
    }

    private void fillCheckBoxList() {
        checkBoxList.add(foodCheckBox);
        checkBoxList.add(electronicCheckBox);
        checkBoxList.add(healthCheckBox);
        checkBoxList.add(styleCheckBox);
        checkBoxList.add(dessertCheckBox);
        checkBoxList.add(sportCheckBox);
        checkBoxList.add(fashionCheckBox);
        checkBoxList.add(craftsCheckBox);
        checkBoxList.add(giftsCheckBox);
        checkBoxList.add(petsCheckBox);
        checkBoxList.add(clothesCheckBox);
        checkBoxList.add(accesoryCheckBox);
        checkBoxList.add(artCheckBox);

        if (!categoriesId.contains(-1)) allCheckBox.setChecked(true);
        checkBoxList.forEach(checkBox -> checkBox.setChecked(categoriesId.contains(Integer.parseInt(checkBox.getTag().toString()))));
    }
}
