package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Entrepreneur extends Account implements Serializable {

    @SerializedName("company_id")
    private int companyId;

    private EntrepreneurModel modelEntrepreneur;

    public Entrepreneur(String userName, String password, String startDate, int age, String email, String phone, String ext, int companyId, EntrepreneurModel modelEntrepreneur, int affiliate) {
        super(userName, password, startDate, age, email, phone, ext, affiliate);
        this.companyId = companyId;
        this.modelEntrepreneur = modelEntrepreneur;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public EntrepreneurModel getModelEntrepreneur() {
        return modelEntrepreneur;
    }

    public void setModelEntrepreneur(EntrepreneurModel modelEntrepreneur) {
        this.modelEntrepreneur = modelEntrepreneur;
    }
}
