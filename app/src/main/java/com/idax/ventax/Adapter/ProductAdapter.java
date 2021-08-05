package com.idax.ventax.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Update;
import com.idax.ventax.Dialog.ImageProductDialog;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.Product;
import com.idax.ventax.Fragment.Product.ProductView;
import com.idax.ventax.R;
import com.idax.ventax.ViewHolder.ProductViewHolder;

import java.text.MessageFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.idax.ventax.Extra.Constansts.AFFILIATE;
import static com.idax.ventax.Extra.Constansts.mainURL;
import static com.idax.ventax.Extra.Util.formatDecimal;
import static com.idax.ventax.Extra.Util.getNumOfItemsByAccountType;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> implements View.OnClickListener {

    private final List<Product> productList;
    private final Design design;
    private Context context;

    private final int affiliate;
    private final int companyId;
    private final Company company;
    private int maxItems;
    private int totalItems;
    private final String companyName;

    private ProductView productView;

    private View.OnClickListener listener;

    private final FragmentManager fragmentManager;

    public ProductAdapter(List<Product> productList, Design design, int affiliate, int companyId, Company company, FragmentManager fragmentManager) {
        this.company = company;
        this.productList = productList;
        this.design = design;
        this.affiliate = affiliate;
        this.companyId = companyId;
        this.companyName = company.getName().toUpperCase();
        this.fragmentManager = fragmentManager;
        totalItems = (int) productList.stream().filter(x -> x.getState() == 1).count();
        this.maxItems = getNumOfItemsByAccountType(company.getPriority());

    }

    public ProductAdapter(List<Product> productList, Design design, int affiliate, int companyId, Company company, FragmentManager fragmentManager, ProductView productView) {
        this.company = company;
        this.productList = productList;
        this.design = design;
        this.affiliate = affiliate;
        this.companyId = companyId;
        this.companyName = company.getName().toUpperCase();
        this.fragmentManager = fragmentManager;
        this.productView = productView;
        totalItems = (int) productList.stream().filter(x -> x.getState() == 1).count();
        this.maxItems = getNumOfItemsByAccountType(company.getPriority());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = (design.getColumns() == 1) ?
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products_one, parent, false) :
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products_two, parent, false);
        v.setOnClickListener(this);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch_ = holder.getStateProduct();
        try {
            ImageView imageView = holder.getImageProductImageView();
            Product product = productList.get(position);
            holder.getTitleProductTextView().setText(product.getName());
            holder.getPriceProductTextView().setText(MessageFormat.format("{0} MXN", formatDecimal(product.getPrice() / 100.0)));
            holder.getDescriptionProductTextView().setText(product.getDescription());
            switch_.setChecked(product.getState() == 1);

            String url = MessageFormat.format("{0}IMAGES/{1}_{2}/{3}_{4}.jpg",
                    mainURL, companyId, companyName, product.getId(), product.getName().replace(" ", "_"));
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.progress_bar_image)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .error(0)
                    .into(imageView);
            imageView.setOnClickListener(v -> {
                new ImageProductDialog(imageView.getDrawable(), url).show(fragmentManager, "ImageProductDialog");
            });
            //holder.getStateProduct().setOnCheckedChangeListener((buttonView, isChecked) -> {
            //    try {
            //        changeStateRequest(isChecked, product, buttonView);
            //    } catch (Exception e) {
            //        Toast.makeText(context, "Listo", Toast.LENGTH_SHORT).show();
            //        e.printStackTrace();
            //    }
            //});
            switch_.setOnClickListener(v -> {
                //Toast.makeText(context, "" + switch_.isChecked(), Toast.LENGTH_SHORT).show();
                if (switch_.isChecked() && totalItems == maxItems) {
                    Toast.makeText(context, "Alcanzó el máximo de productos disponibles", Toast.LENGTH_SHORT).show();
                    switch_.setChecked(!switch_.isChecked());
                    return;
                }
                try {
                    changeStateRequest(holder.getStateProduct().isChecked(), product, holder.getStateProduct(), position);
                } catch (Exception e) {
                    Toast.makeText(context, "Listo", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });

            //DISEÑO
            holder.getTitleProductTextView().setTextColor(Color.parseColor(design.getTitle_color()));
            holder.getPriceProductTextView().setTextColor(Color.parseColor(design.getTitle_color()));
            holder.getDescriptionProductTextView().setTextColor(Color.parseColor(design.getText_color()));
            holder.getBackgroundCardViewLinearLayout().setBackgroundColor(Color.parseColor(design.getCard_background()));
        } catch (Exception e) {
            switch_.setVisibility(View.GONE);
            holder.getImageProductImageView().setBackgroundColor(context.getResources().getColor(R.color.blueColorVentax));
            holder.getImageProductImageView().setImageDrawable(context.getDrawable(R.drawable.ic_baseline_library_add_24));
        }
        if (affiliate != AFFILIATE) {
            switch_.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return ((affiliate == AFFILIATE) ? productList.size() + 1 : productList.size());
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    private void changeStateRequest(boolean isChecked, final Product product, CompoundButton buttonView, int position) {
        Update update = Conn.getClient().create(Update.class);
        Call<String> call = update.UpdateProductState(product.getId(), isChecked ? 1 : 0);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().equals("1")) {
                        product.setState(isChecked ? 1 : 0);
                        totalItems = totalItems + (isChecked ? 1 : -1);
                        Toast.makeText(context, MessageFormat.format("Cambió de estado {0} exitosamente", product.getName()), Toast.LENGTH_SHORT).show();
                        productView.onSuccessUpdate(product, position, totalItems);
                        return;
                    }
                }
                Toast.makeText(context, MessageFormat.format("No se pudo actualziar el estado de {0}", product.getName()), Toast.LENGTH_SHORT).show();
                if (buttonView instanceof Switch) {
                    buttonView.setChecked(!isChecked);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, MessageFormat.format("No se pudo actualziar el estado de {0}", product.getName()), Toast.LENGTH_SHORT).show();
                if (buttonView instanceof Switch) {
                    buttonView.setChecked(!isChecked);
                }
            }
        });
    }
}
