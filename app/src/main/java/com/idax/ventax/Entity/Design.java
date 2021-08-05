package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Design implements Serializable {

    @SerializedName("columns")
    private int columns;

    @SerializedName("main_background")
    private String main_background;

    @SerializedName("card_background")
    private String card_background;

    @SerializedName("title_color")
    private String title_color;

    @SerializedName("text_color")
    private String text_color;

    @SerializedName("dialog_background")
    private String dialog_background;

    public Design() {
        this.columns = 1;
        this.main_background = "#009CE7";
        this.card_background = "#EDA145";
        this.title_color = "#000000";
        this.text_color = "#FFFFFF";
        this.dialog_background = "#EDA145";
    }

    public Design(int columns, String main_background, String card_background, String title_color, String text_color, String dialog_background) {
        this.columns = columns;
        this.main_background = main_background;
        this.card_background = card_background;
        this.title_color = title_color;
        this.text_color = text_color;
        this.dialog_background = dialog_background;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public String getMain_background() {
        return main_background;
    }

    public void setMain_background(String main_background) {
        this.main_background = main_background;
    }

    public String getCard_background() {
        return card_background;
    }

    public void setCard_background(String card_background) {
        this.card_background = card_background;
    }

    public String getTitle_color() {
        return title_color;
    }

    public void setTitle_color(String title_color) {
        this.title_color = title_color;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public String getDialog_background() {
        return dialog_background;
    }

    public void setDialog_background(String dialog_background) {
        this.dialog_background = dialog_background;
    }
}
