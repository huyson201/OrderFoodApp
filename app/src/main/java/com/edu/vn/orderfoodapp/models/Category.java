package com.edu.vn.orderfoodapp.models;

public class Category {
    private String categoryName;
    private String categoryId;
    private String categoryImg;
    public  Category(){}
    public Category(String categoryName, String categoryId,String categoryImg) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.categoryImg = categoryImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryId;
    }

    public void setCategoryID(String categoryID) {
        this.categoryId = categoryID;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }
}
