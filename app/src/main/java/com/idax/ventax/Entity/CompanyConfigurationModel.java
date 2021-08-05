package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CompanyConfigurationModel implements Serializable {

    @SerializedName("design")
    private Design design;

    @SerializedName("schedules")
    private List<Schedule> scheduleList;

    @SerializedName("social_media")
    private List<SocialMedia> socialMediaList;

    @SerializedName("company_id")
    private int companyId;

    public CompanyConfigurationModel(Design design, List<Schedule> scheduleList, List<SocialMedia> socialMediaList, int companyId) {
        this.design = design;
        this.scheduleList = scheduleList;
        this.socialMediaList = socialMediaList;
        this.companyId = companyId;
    }

    public Design getDesign() {
        return design;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public List<SocialMedia> getSocialMediaList() {
        return socialMediaList;
    }
}
