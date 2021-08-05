package com.idax.ventax.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idax.ventax.Entity.CompanySQLite;
import com.idax.ventax.Fragment.Menu.FavCompanyView;
import com.idax.ventax.R;
import com.idax.ventax.RoomDB.AppDataBase.AppDataBase;
import com.idax.ventax.RoomDB.DAO.CompanyDao;
import com.idax.ventax.ViewHolder.FavViewHolder;

import java.text.MessageFormat;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.mainURL;

public class FavAdapter extends RecyclerView.Adapter<FavViewHolder> implements View.OnClickListener {

    private final List<CompanySQLite> companySQLiteList;
    private final FavCompanyView favCompanyView;
    private final Context context;

    private CompanyDao companyDao;

    public FavAdapter(List<CompanySQLite> companySQLiteList, FavCompanyView favCompanyView, Context context) {
        this.companySQLiteList = companySQLiteList;
        this.favCompanyView = favCompanyView;
        this.context = context;

        companyDao = AppDataBase.getAppDataBase(context).companyDao();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_company, parent, false);
        view.setOnClickListener(this);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        CompanySQLite company = companySQLiteList.get(position);

        holder.getNameTextView().setText(company.getName());
        holder.getFavImageButton().setOnClickListener(v -> {
            companyDao.deleteFavCompany(company);
            favCompanyView.OnDeleteFavFromFavs(company, position);
        });
        String url = MessageFormat.format("{0}IMAGES/{1}_{2}/{2}.jpg",
                mainURL, company.getId(), company.getName().toUpperCase());
        Glide.with(context)
                .load(url)
                .error(0)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(holder.getLogoImageView());
    }

    @Override
    public int getItemCount() {
        return companySQLiteList.size();
    }

    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}
