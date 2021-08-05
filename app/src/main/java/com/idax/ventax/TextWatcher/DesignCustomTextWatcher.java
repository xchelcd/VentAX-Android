package com.idax.ventax.TextWatcher;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DesignCustomTextWatcher implements TextWatcher {
    private LinearLayout linearLayout;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    public DesignCustomTextWatcher(TextView textView1, TextView textView2, TextView textView3, TextView textView4) {
        this.textView1 = textView1;
        this.textView2 = textView2;
        this.textView3 = textView3;
        this.textView4 = textView4;
    }

    public DesignCustomTextWatcher(TextView textView1, TextView textView2) {
        this.textView1 = textView1;
        this.textView2 = textView2;
    }

    public DesignCustomTextWatcher(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            if(linearLayout != null) setColor(linearLayout, editable.toString());
            else if (textView1 != null && textView3 == null) setColor(textView1, textView2, editable.toString());
            else if (textView3 != null) setColor(textView1, textView2, textView3, textView4, editable.toString());
        } catch (Exception e) {

        }
    }



    private void setColor(LinearLayout linearLayout, String color){
        try {
            linearLayout.setBackgroundColor(Color.parseColor(parseColor(color)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setColor(TextView textView1, TextView textView2, String color){
        try {
            textView1.setTextColor(Color.parseColor(parseColor(color)));
            textView2.setTextColor(Color.parseColor(parseColor(color)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setColor(TextView textView1, TextView textView2, TextView textView3, TextView textView4, String color) {
        try {
            textView1.setTextColor(Color.parseColor(parseColor(color)));
            textView2.setTextColor(Color.parseColor(parseColor(color)));
            textView3.setTextColor(Color.parseColor(parseColor(color)));
            textView4.setTextColor(Color.parseColor(parseColor(color)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parseColor(String color) {
        if(color.startsWith("#") && color.length() == 7){
            return color;
        }else if(!color.startsWith("#") && color.length() == 6){
            return "#" + color;
        }else{
            return null;
        }
    }
}