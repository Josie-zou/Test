package com.example.josie.testas.Model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Josie on 2015/12/12.
 */
public class TimeModel {
//    private int iconimageView;
    private String nametextview;
    private int id;
    private String contenttextview;
    private String timetextview;

    public TimeModel(String nametextview, String contenttextview, String timetextview) {
//        this.iconimageView = iconimageView;
        this.nametextview = nametextview;
        this.contenttextview = contenttextview;
        this.timetextview = timetextview;
    }

    public TimeModel() {

    }

//    public int getIconimageView() {
//        return iconimageView;
//    }
//
//    public void setIconimageView(int iconimageView) {
//        this.iconimageView = iconimageView;
//    }

//    public int getMeimageView() {
//        return meimageView;
//    }
//
//    public void setMeimageView(int meimageView) {
//        this.meimageView = meimageView;
//    }

    public String getNametextview() {
        return nametextview;
    }

    public void setNametextview(String nametextview) {
        this.nametextview = nametextview;
    }

    public String getContenttextview() {
        return contenttextview;
    }

    public void setContenttextview(String contenttextview) {
        this.contenttextview = contenttextview;
    }

    public String getTimetextview() {
        return timetextview;
    }

    public void setTimetextview(String timetextview) {
        this.timetextview = timetextview;
    }
}
