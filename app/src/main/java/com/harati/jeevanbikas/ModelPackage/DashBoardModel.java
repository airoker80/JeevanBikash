package com.harati.jeevanbikas.ModelPackage;

import android.widget.ImageView;

/**
 * Created by Sameer on 8/28/2017.
 */

public class DashBoardModel {
    int dashboard_icon_id;
    String dashboard_icon_name;

    public DashBoardModel(int dashboard_icon_id, String dashboard_icon_name) {
        this.dashboard_icon_id = dashboard_icon_id;
        this.dashboard_icon_name = dashboard_icon_name;
    }
    public int getDashboard_icon_id() {
        return dashboard_icon_id;
    }

    public void setDashboard_icon_id(int dashboard_icon_id) {
        this.dashboard_icon_id = dashboard_icon_id;
    }

    public String getDashboard_icon_name() {
        return dashboard_icon_name;
    }

    public void setDashboard_icon_name(String dashboard_icon_name) {
        this.dashboard_icon_name = dashboard_icon_name;
    }
}
