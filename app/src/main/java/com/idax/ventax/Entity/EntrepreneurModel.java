package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntrepreneurModel implements Serializable {

    @SerializedName("Company")
    private Company company;

    @SerializedName("Design")
    private Design design;

    @SerializedName("Products")
    private List<Product> productList;

    @SerializedName("Schedules")
    private List<Schedule> scheduleList;

    @SerializedName("FAQs")
    private List<FAQ> faqList;

    @SerializedName("SocialMedias")
    private List<SocialMedia> socialMediaList;

    public EntrepreneurModel() {
        company = new Company();
        design = new Design();
        productList = new ArrayList<>();
        scheduleList = new ArrayList<>();
        faqList = new ArrayList<>();
        socialMediaList = new ArrayList<>();
    }

    public EntrepreneurModel(Company company, Design design, List<Product> productList, List<Schedule> scheduleList, List<FAQ> faqList, List<SocialMedia> socialMediaList) {
        this.company = company;
        this.design = design;
        this.productList = productList;
        this.scheduleList = scheduleList;
        this.faqList = faqList;
        this.socialMediaList = socialMediaList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Design getDesign() {
        return design;
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<FAQ> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<FAQ> faqList) {
        this.faqList = faqList;
    }

    public List<SocialMedia> getSocialMediaList() {
        return socialMediaList;
    }

    public void setSocialMediaList(List<SocialMedia> socialMediaList) {
        this.socialMediaList = socialMediaList;
    }
}
