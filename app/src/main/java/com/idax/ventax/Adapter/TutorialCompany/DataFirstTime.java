package com.idax.ventax.Adapter.TutorialCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DataFirstTime {

    private String title;
    private String text;

    public static List<DataFirstTime> getDataList(Context context){
        List<DataFirstTime> dataList = new ArrayList<>();
        //todo fill list
        return  dataList;
    }

    private static View getView(Context context, int view){
        if (view == 0) return null;
        return LayoutInflater.from(context).inflate(view, null, false);
    }

    //todo: getter setter
}
