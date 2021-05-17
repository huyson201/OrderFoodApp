package com.edu.vn.orderfoodapp.models;


public class AdminMenu {
    private int imgId;
    private String name;

    public static final String PROFILE_TAG = "Profile";
    public static final String LOGOUT_TAG = "Logout";
    public static final String WAITING_INVOICE_TAG = "Waiting Invoices";
    public static final String CONFIRMED_INVOICE_TAG = "Confirmed Invoices";

    public AdminMenu(int imgId, String name) {
        this.imgId = imgId;
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
