package com.idax.ventax.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.CompanySQLite;
import com.idax.ventax.Fragment.Menu.FavCompanyView;
import com.idax.ventax.R;
import com.idax.ventax.RoomDB.AppDataBase.AppDataBase;
import com.idax.ventax.RoomDB.DAO.CompanyDao;
import com.idax.ventax.ViewHolder.CompanyViewHolder;

import java.text.MessageFormat;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.mainURL;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyViewHolder> implements View.OnClickListener {

    private final List<Company> companyList;
    private final Context context;
    private CompanyDao companyDao;
    private FavCompanyView favCompanyView;

    public CompanyAdapter(List<Company> companyList, FavCompanyView favCompanyView, Context context) {
        this.companyList = companyList;
        this.favCompanyView = favCompanyView;
        this.context = context;

        companyDao = AppDataBase.getAppDataBase(context).companyDao();
        //idList = companyDao.GetAllFavCompanies().stream().map(CompanySQLite::getId).collect(Collectors.toList());
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business, parent, false);
        v.setOnClickListener(this);
        return new CompanyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        ImageButton imageButton = holder.getFavImageButton();
        Company company = companyList.get(position);

        holder.getNameTextView().setText(company.getName());
        if (companyDao.hasExist(company.getId()))
            imageButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24));
        imageButton.setOnClickListener(v -> {
            if (!companyDao.hasExist(company.getId())) {
                CompanySQLite companySQLite = retrieveCompany(company);
                companyDao.insertFavCompany(companySQLite);
                favCompanyView.OnInsertCompany(companySQLite);
                imageButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24));
                Toast.makeText(context, MessageFormat.format("{0} anañido a fav", companySQLite.getName().toUpperCase()), Toast.LENGTH_SHORT).show();
            } else {
                CompanySQLite companySQLite = companyDao.getCompanyById(company.getId());
                companyDao.deleteFavCompany(companySQLite);
                favCompanyView.OnDeleteFavCompany(companySQLite);
                imageButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_border_24));
            }
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

    private CompanySQLite retrieveCompany(Company company) {
        return new CompanySQLite(company.getId(), company.getName(), company.getDescription(), company.getCategory(), company.getPriority());
    }

    @Override
    public int getItemCount() {
        return companyList.size();
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
