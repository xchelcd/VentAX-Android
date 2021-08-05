package com.idax.ventax.Dialog.Product;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.Product;
import com.idax.ventax.Extra.LoadingDialog;
import com.idax.ventax.R;

import java.io.IOException;
import java.text.MessageFormat;

import static android.app.Activity.RESULT_OK;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_COMPANY_PHOTO;
import static com.idax.ventax.Extra.Constansts.mainURL;
import static com.idax.ventax.Extra.Util.formatDecimal;

public class ProductDialog extends DialogFragment implements ProductView {

    private int companyId;
    private String companyName;
    private Product product;
    private Design design;
    private boolean add;
    private com.idax.ventax.Fragment.Product.ProductView view;

    private LoadingDialog dialog;

    private ImageView oneProductImageView;
    //private ImageView twoProductImageView;
    //private ImageView threeProductImageView;
    private EditText titleProductEditText;
    private EditText priceProductEditText;
    private EditText detailsProductEditText;
    private EditText descriptionProductEditText;
    private TextView idProductEditText;
    private Button addProductButton;
    private Button deleteProductUButton;
    private LinearLayout backgroundDialogLinearLayout;

    private ImageButton closeImageButton;

    private ProductPresenter presenter;

    private Bitmap bitmap;

    private Drawable drawable;

    public ProductDialog(int companyId, String companyName, Product product, Design design, boolean add, com.idax.ventax.Fragment.Product.ProductView view) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.product = product;
        this.design = design;
        this.add = add;
        this.view = view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            receiveData(data);
        } catch (Exception e) {
            Toast.makeText(getContext(), "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == REQUEST_CODE_COMPANY_PHOTO) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Subiendo imagen", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.error_update_photo, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_product, null, false);
        builder.setView(view);
        inits(view);
        listeners();
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private void listeners() {
        addProductButton.setOnClickListener(v -> {
            if (checkText()) {
                getProductData();
                if (add) {
                    if (bitmap == null) {
                        Toast.makeText(getContext(), "Requiere poner una image", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    presenter.AddNewProduct(companyId, companyName, product, bitmap);
                } else {
                    presenter.EditProduct(companyId, companyName, product, bitmap);
                }

            } else
                Toast.makeText(getContext(), "Faltan campos por llenar", Toast.LENGTH_SHORT).show();

        });
        deleteProductUButton.setOnClickListener(v -> {
            presenter.DeleteProduct(companyId, product.getId());
        });
        closeImageButton.setOnClickListener(v -> {
            dismiss();
        });

        oneProductImageView.setOnClickListener(v -> {
            openGallery(REQUEST_CODE_COMPANY_PHOTO);
        });
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecione una aplicación"), requestCode);
    }

    private void getProductData() {
        product.setDescription(descriptionProductEditText.getText().toString());
        product.setDetails(detailsProductEditText.getText().toString());
        product.setName(titleProductEditText.getText().toString());
        product.setPosition(0);
        String price = priceProductEditText.getText().toString().replace("$", "");
        product.setPrice(Double.parseDouble(price) * 100);
        product.setState(0);
    }

    private void inits(View v) {
        presenter = new ProductPresenter(this);
        titleProductEditText = v.findViewById(R.id.titleProductEditText);
        priceProductEditText = v.findViewById(R.id.priceProductEditText);
        detailsProductEditText = v.findViewById(R.id.detailsProductEditText);
        descriptionProductEditText = v.findViewById(R.id.descriptionProductEditText);
        idProductEditText = v.findViewById(R.id.idProductTextView);
        addProductButton = v.findViewById(R.id.addProductButton);
        deleteProductUButton = v.findViewById(R.id.deleteProductUButton);
        backgroundDialogLinearLayout = v.findViewById(R.id.backgroundDialogLinearLayout);
        closeImageButton = v.findViewById(R.id.closeImageButton);
        oneProductImageView = v.findViewById(R.id.oneProductImageView);
        if (add) {
            deleteProductUButton.setVisibility(View.GONE);
            addProductButton.setText("AGREGAR");
        } else addProductButton.setText("EDITAR");
        setData();

        dialog = new LoadingDialog(getContext());
    }

    private void setData() {
        try {
            String url = MessageFormat.format("{0}IMAGES/{1}_{2}/{3}_{4}.jpg",
                    mainURL, companyId, companyName, product.getId(), product.getName().replace(" ", "_"));
            Glide.with(this).asBitmap()
                    .load(url)
                    .placeholder(R.drawable.progress_bar_image)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    //.centerCrop()
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            oneProductImageView.setImageBitmap(resource);
                            bitmap = resource;
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        backgroundDialogLinearLayout.setBackgroundColor(Color.parseColor(design.getDialog_background()));
        titleProductEditText.setText(product.getName());
        priceProductEditText.setText((add) ? "" : formatDecimal(product.getPrice() / 100));
        idProductEditText.setText(MessageFormat.format("ID: {0}", product.getId()));
        descriptionProductEditText.setText(product.getDescription());
        detailsProductEditText.setText(product.getDetails());
    }

    private boolean checkText() {
        return !(titleProductEditText.getText().toString().equals("") || priceProductEditText.getText().toString().equals(""));
    }

    private void receiveData(Intent data) {
        try {
            Uri path = data.getData();
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
            //oneProductImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            oneProductImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnAddSuccess(Product product) {
        dismiss();
        view.onSuccessInsert(product);
    }

    @Override
    public void OnAddError(String message) {
        view.onError(message);
    }

    @Override
    public void OnEditSuccess(Product product) {
        dismiss();
        view.onSuccessUpdate(product, -1, -1);
    }

    @Override
    public void OnEditError(String message) {
        view.onError(message);
    }

    @Override
    public void OnDeleteSuccess(String message) {
        view.onSuccessDelete(message);
        dismiss();
    }

    @Override
    public void OnDeleteError(String message) {
        view.onError(message);
    }

    @Override
    public void showProgressBar(String message) {
        dialog.openLoadingDialog(message);
    }

    @Override
    public void hideProgressBar() {
        dialog.closeLoadingDialog();
    }
}
