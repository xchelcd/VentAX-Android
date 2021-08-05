package com.idax.ventax.Activity.Menu;

import android.os.Parcelable;

import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.FAQ;
import com.idax.ventax.Entity.Product;
import com.idax.ventax.Entity.Schedule;
import com.idax.ventax.Entity.SocialMedia;
import com.idax.ventax.Entity.User;

import java.io.Serializable;
import java.util.List;

public interface MenuUpdateObjects extends Parcelable {

    void UpdateProfile(User user);
    void UpdateCompany(Company company);
    void UpdateCompanySettings(Design design, List<Schedule> scheduleList, List<SocialMedia> socialMediaList);
    void UpdateProducts(List<Product> productList);
    void UpdateFAQList(List<FAQ> faqList);

    void onSuccessLogOut();
    void onErrorLogOut(String message);

    void showProgressBar(String message);
    void hideProgressBar();
}
