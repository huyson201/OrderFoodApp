package com.edu.vn.orderfoodapp.models;


public class AdminMenu {
    private int imgId;
    private String name;

    public static final String PROFILE_TAG = "Profile";
    public static final String LOGOUT_TAG = "Logout";
    public static final String WAITING_BILL_TAG = "Waiting Bills";
    public static final String CONFIRMED_BILL_TAG = "Completed Bills";
    public static final String DELIVERY_TAG = "Delivery";
    public static final String MENUS_TAG = "Menu";
    public static final String COOKING_TAG = "Cooking";
    public static final String CATEGORY_TAG = "Categories";
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
