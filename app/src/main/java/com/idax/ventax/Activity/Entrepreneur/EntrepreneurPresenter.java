package com.idax.ventax.Activity.Entrepreneur;


import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Insert;
import com.squareup.okhttp.ResponseBody;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntrepreneurPresenter {

    private EntrepreneurView view;

    public EntrepreneurPresenter(EntrepreneurView view) {
        this.view = view;
    }


}
