package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FAQModel implements Serializable {

    @SerializedName("company_id")
    private int companyId;

    @SerializedName("faqs")
    private List<FAQ> faqList;

    public FAQModel(int companyId, List<FAQ> faqList) {
        this.companyId = companyId;
        this.faqList = faqList;
    }
}
