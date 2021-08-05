package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginModel implements Serializable {

    @SerializedName("User")
    private User user;

    @SerializedName("Company")
    private List<Company> companyList;

    @SerializedName("ModelEntrepreneur")
    private EntrepreneurModel entrepreneurModel;

    public LoginModel() {
        companyList = new ArrayList<>();
        entrepreneurModel = new EntrepreneurModel();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public EntrepreneurModel getEntrepreneurModel() {
        return entrepreneurModel;
    }

    public void setEntrepreneurModel(EntrepreneurModel entrepreneurModel) {
        this.entrepreneurModel = entrepreneurModel;
    }
}
