package com.edu.vn.orderfoodapp.models;

public class Category {
    private String categoryName;
    private String categoryID;
    private String categoryImg;

    public Category(String categoryName, String categoryID,String categoryImg) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.categoryImg = categoryImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }
}
