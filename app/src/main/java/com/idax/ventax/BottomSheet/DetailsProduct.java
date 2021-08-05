package com.idax.ventax.BottomSheet;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.idax.ventax.Activity.Entrepreneur.EntrepreneurPresenter;
import com.idax.ventax.Dialog.ImageProductDialog;
import com.idax.ventax.Dialog.Order.OrderDialog;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.Product;
import com.idax.ventax.Entity.User;
import com.idax.ventax.R;

import java.text.MessageFormat;

import static com.idax.ventax.Extra.Constansts.mainURL;
import static com.idax.ventax.Extra.Util.formatDecimal;

public class DetailsProduct extends BottomSheetDialogFragment {

    private ImageView oneDetailsImageView;
    private TextView titleDetailsEditText;
    private TextView priceDetailsEditText;
    private TextView detailsDetailsEditText;
    private TextView idDetailsEditText;
    private Button orderDetailsButton;
    private Button cancelDetailsButton;
    private LinearLayout mainDetailsLinearLayout;

    private Drawable background;

    private Company company;
    private Product product;
    private Design design;
    private User user;

    private String url;

    public DetailsProduct(Company company, Product product, Design design, User user) {
        this.company = company;
        this.product = product;
        this.design = design;
        this.user = user;
    }

    //TODO crear método para definir el número de imagenes en la parte superior de @dialog_product

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_product, null, false);
        inits(v);
        setDetails();
        //dialog.setView(v);
        listeners();
        //AlertDialog alertDialog = dialog.create();
        //alertDialog.setCanceledOnTouchOutside(false);
        //alertDialog.setCancelable(false);
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //alertDialog.show();
        //return alertDialog;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.getWindow()
                .findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        bottomSheetDialog.show();
        return bottomSheetDialog;
    }

    private void setDetails() {
        ((GradientDrawable) background).setColor(Color.parseColor(design.getDialog_background()));
        titleDetailsEditText.setText(product.getName());
        priceDetailsEditText.setText(MessageFormat.format("{0} MXN", formatDecimal(product.getPrice() / 100)));
        detailsDetailsEditText.setText(product.getDetails());
        idDetailsEditText.setText(MessageFormat.format("ID: {0}", product.getId()));

        //mainDetailsLinearLayout.setBackgroundColor(Color.parseColor(design.getDialog_background()));
        titleDetailsEditText.setTextColor(Color.parseColor(design.getTitle_color()));
        priceDetailsEditText.setTextColor(Color.parseColor(design.getTitle_color()));
        detailsDetailsEditText.setTextColor(Color.parseColor(design.getText_color()));
        idDetailsEditText.setTextColor(Color.parseColor(design.getText_color()));

        url = MessageFormat.format("{0}IMAGES/{1}_{2}/{3}_{4}.jpg",
                mainURL, company.getId(), company.getName(), product.getId(), product.getName().replace(" ", "_"));
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.progress_bar_image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(oneDetailsImageView);
    }

    private void listeners() {
        orderDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OrderDialog(user, product, company).show(getParentFragmentManager(), "OrderDialog");
            }
        });
        cancelDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        oneDetailsImageView.setOnClickListener(v -> {
            new ImageProductDialog(oneDetailsImageView.getDrawable(), url).show(getParentFragmentManager(), "ImageProductDialog");
        });
    }

    private void inits(View v) {
        oneDetailsImageView = v.findViewById(R.id.oneDetailsImageView);
        titleDetailsEditText = v.findViewById(R.id.titleDetailsTextView);
        priceDetailsEditText = v.findViewById(R.id.priceDetailsTextView);
        detailsDetailsEditText = v.findViewById(R.id.detailsDetailsTextView);
        idDetailsEditText = v.findViewById(R.id.idDetailsTextView);
        orderDetailsButton = v.findViewById(R.id.orderDetailsButton);
        cancelDetailsButton = v.findViewById(R.id.cancelDetailsButton);
        mainDetailsLinearLayout = v.findViewById(R.id.mainDetailsLinearLayout);

        background = v.findViewById(R.id.bottom_sheet_p).getBackground();
    }
}
